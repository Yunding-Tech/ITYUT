package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.dto.ExamInfo;

import java.util.List;

public interface ITyutExamServer {
    List<ExamInfo> getExamSchedule(String account);
}
