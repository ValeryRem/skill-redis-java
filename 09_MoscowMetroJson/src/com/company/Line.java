package com.company;

public class Line {
    String lineNumber;
    String lineName;

    public Line (){
    }

    public Line (String lineNumber, String lineName) {
        this.lineNumber = lineNumber;
        this.lineName = lineName;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Line line = (Line) obj;
        return
                (lineName == line.lineName)||
                        (lineName != null && lineName.equals(line.getLineName())) && (lineNumber == line.lineNumber) ||
                                (lineNumber != null &&  lineNumber.equals(line.getLineNumber())
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lineName == null) ? 0 : lineName.hashCode());
        result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
        return
                result;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getLineName() {
        return lineName;
    }
}
