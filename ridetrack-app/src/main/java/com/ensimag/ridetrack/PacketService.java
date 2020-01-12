package com.ensimag.ridetrack;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.models.DeviceData;
import com.ensimag.ridetrack.radio.geoloc.DeviceLocation;
import com.ensimag.ridetrack.radio.geoloc.GeolocationService;
import com.ensimag.ridetrack.radio.packets.PacketQueueHandler;
import com.ensimag.ridetrack.radio.packets.RtPacket;
import com.ensimag.ridetrack.repository.DeviceDataRepository;
import com.ensimag.ridetrack.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PacketService {
	
	private static final int THREAD_POOL_SIZE = 3;
	
	private static final int THREAD_POOL_MAX_SIZE = 10;
	
	private final ThreadPoolTaskExecutor executor;
	
	private boolean shouldStop = false;
	
	@Autowired
	private PacketQueueHandler packetQueueHandler;
	
	@Autowired
	private GeolocationService geolocationService;
	
	@Autowired
	private DeviceDataRepository deviceDataRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	private final PlatformTransactionManager transactionManager;
	
	public PacketService(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(THREAD_POOL_SIZE);
		executor.setMaxPoolSize(THREAD_POOL_MAX_SIZE);
		executor.setThreadNamePrefix("rt_packet_executor");
		executor.initialize();
	}
	
	public void startPacketExecutors() {
		log.info("Starting packet executors");
		for (int i = 0; i < 4; i++) {
			executor.execute(() -> {
				log.debug("Starting thread {}", Thread.currentThread().getName());
				BlockingQueue<RtPacket> queue = packetQueueHandler.getQueue();
				while (!shouldStop) {
					try {
						// Blocking queue
						RtPacket rtPacket = queue.take();
						TransactionTemplate transactionTemplate = getTransactionTemplate();
						DeviceLocation location = geolocationService.locateTTNPacket(rtPacket.getRawPayload());
						createNewDeviceDate(rtPacket, location, transactionTemplate);
						
					} catch (InterruptedException e) {
						log.info("Thread {} interrupted", Thread.currentThread().getName(), e);
						Thread.currentThread().interrupt();
					}
				}
			});
		}
	}
	
	private synchronized TransactionTemplate getTransactionTemplate() {
		return new TransactionTemplate(transactionManager);
	}
	
	public void createNewDeviceDate(RtPacket rtPacket, DeviceLocation location, TransactionTemplate transactionTemplate) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Optional<Device> deviceOpt = deviceRepository.findByDeviceUid(location.getDeviceUid());
				if (deviceOpt.isEmpty()) {
					log.error("Device '{}' not found. Skipping", location.getDeviceUid());
				} else {
					Device device = deviceOpt.get();
					DeviceData deviceData = new DeviceData();
					deviceData.setDevice(device);
					deviceData.setLatitude(location.getLatitude().toString());
					deviceData.setLongitude(location.getLongitude().toString());
					deviceData.setCreatedAt(rtPacket.getTimestamp());
					device.addDeviceData(deviceData);
					deviceRepository.save(device);
					log.debug("Saved new device data to device {}", device.getDeviceUid());
				}
			}
		});
	}
	
	public void stopExecutors() {
		this.shouldStop = true;
	}
	
}
