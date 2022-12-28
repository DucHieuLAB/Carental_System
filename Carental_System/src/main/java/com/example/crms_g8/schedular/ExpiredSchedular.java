package com.example.crms_g8.schedular;

import com.example.crms_g8.Entity.ContractEntity;
import com.example.crms_g8.Service.IService.ContractService;
import com.example.crms_g8.Service.ServiceImpl.ContractServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpiredSchedular {
    @Autowired
    ContractServiceImpl contractService;

    @SneakyThrows
    @Scheduled(cron = "0 0 13 * * ?",zone = "Asia/Jakarta")
    public void checkInvalidContract(){
        List<ContractEntity> contractEntities = contractService.getListInvalidContract();
        if (contractEntities.size() > 0){
            for (ContractEntity contractEntity : contractEntities){
                if (contractEntity.isHad_driver()){
                    contractEntity.setStatus(7);
                    contractEntity.setNote("Hợp đồng quá hạn tài xế chưa lấy xe");
                    contractService.save(contractEntity);
                }else {
                    if (contractEntity.getPickup_parking().getId() == contractEntity.getReturn_parking().getId()){
                        contractEntity.setStatus(7);
                        contractEntity.setNote("ER101 Hợp đồng quá hạn! Cảnh báo hợp đồng thay đổi bãi đỗ xe");
                        contractService.save(contractEntity);
                    }else {
                        contractEntity.setStatus(7);
                        contractEntity.setNote("Hợp đồng quá hạn");
                        contractService.save(contractEntity);
                    }
                }

            }
        }
    }
}
