package com.ruoyi.repurchase.service;

import com.ruoyi.repurchase.domain.MemberConsumptionTrack;
import com.ruoyi.repurchase.domain.dto.request.MemberConsumptionTrackRequest;

import java.util.List;

public interface IRepurchaseIndexService {
    List<MemberConsumptionTrack> selectList(MemberConsumptionTrackRequest memberConsumptionTrackRequest);

    String importConsumption(List<MemberConsumptionTrack> consumptionList, boolean updateSupport);
}
