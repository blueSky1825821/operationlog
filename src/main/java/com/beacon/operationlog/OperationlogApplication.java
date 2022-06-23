package com.beacon.operationlog;

import com.beacon.operationlog.start.annotation.EnableLogRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLogRecord(tenant = "com.beacon")
public class OperationlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationlogApplication.class, args);
	}


}
