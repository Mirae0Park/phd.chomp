package com.phd.chomp.service;

import com.phd.chomp.dto.ShippingDto;
import com.phd.chomp.entity.Member;

public interface ShippingService {


    public ShippingDto getDefaultAddr(String uid);
}
