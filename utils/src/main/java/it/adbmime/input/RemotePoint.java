package it.adbmime.input;

public record RemotePoint(int x, int y) {
    @Override
    public String toString() {
        return "RemotePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
