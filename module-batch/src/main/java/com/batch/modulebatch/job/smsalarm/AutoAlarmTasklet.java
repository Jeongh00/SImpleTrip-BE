package com.batch.modulebatch.job.smsalarm;

import com.simpletripbe.moduledomain.batch.api.BatchService;
import com.simpletripbe.moduledomain.batch.dto.AlarmSendDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@JobScope
public class AutoAlarmTasklet implements Tasklet {

    private final BatchService batchService;
    private final MainCarrierService mainCarrierService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        try {

            List<TicketListDTO> ticketList = mainCarrierService.selectCarrierList();
            boolean isAlarmCreated = false;

            for (int i=0; i<ticketList.size(); i++) {

                if(LocalDate.now().isBefore(ticketList.get(i).getStartDate())) {

                    AlarmSendDTO dto = new AlarmSendDTO();
                    dto.setMessage("여행이 시작되었습니다!");
                    dto.setName(ticketList.get(i).getName());
                    dto.setStartDate(ticketList.get(i).getStartDate());
                    dto.setEndDate(ticketList.get(i).getEndDate());

                    batchService.saveStartAlarm(dto);
                    isAlarmCreated = true;

                } else if(LocalDate.now().isAfter(ticketList.get(i).getEndDate())) {

                    AlarmSendDTO dto = new AlarmSendDTO();
                    dto.setMessage("여행이 종료되었습니다!");
                    dto.setName(ticketList.get(i).getName());
                    dto.setStartDate(ticketList.get(i).getStartDate());
                    dto.setEndDate(ticketList.get(i).getEndDate());

                    batchService.saveEndAlarm(dto);
                    isAlarmCreated = true;

                }

            }

            if (isAlarmCreated) {
                return RepeatStatus.FINISHED;
            } else {
                return RepeatStatus.CONTINUABLE;
            }

        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("날짜 형식이 올바르지 않습니다.(YYYY-MM-DD)");
        }
    }
}