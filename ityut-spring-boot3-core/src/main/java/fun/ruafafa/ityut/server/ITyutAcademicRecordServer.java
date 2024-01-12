package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.dto.GradeReport;

public interface ITyutAcademicRecordServer {

    GradeReport getGradeReport(String account);
}
