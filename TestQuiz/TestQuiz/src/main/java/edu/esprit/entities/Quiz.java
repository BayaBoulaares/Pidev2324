package edu.esprit.entities;

import com.google.protobuf.DescriptorProtos;

public class Quiz extends Question {
    public static DescriptorProtos.FieldDescriptorProto.Label Type;
    private int idqz;
    private Type type;
    private int score;

    public Quiz(int idqz, Type type, int score) {
        super();
        this.idqz = idqz;
        this.type = type;
        this.score = score;
    }

    public Quiz(int idqz, DescriptorProtos.FieldDescriptorProto.Label label, int score) {
    }

    // Getters and setters

    public int getIdqz() {
        return idqz;
    }

    public void setIdqz(int idqz) {
        this.idqz = idqz;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "idqz=" + idqz +
                ", type=" + type +
                ", score=" + score +
                '}';
    }

    public int getIdq() {
        return 0;
    }
}
