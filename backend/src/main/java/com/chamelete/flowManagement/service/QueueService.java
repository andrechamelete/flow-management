package com.chamelete.flowManagement.service;

import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.security.dto.metrics.QueueResponse;

public interface QueueService {
    
    QueueResponse getQueue(Flows flow);
}
