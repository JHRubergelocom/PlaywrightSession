package session;

public class PlaywrightConfig {
    private final boolean notHeadless;
    private final boolean recordVideo;
    private final boolean screenShots;
    private final boolean snapShots;
    private final boolean sources;
    private final boolean pause;
    public PlaywrightConfig(boolean notHeadless, boolean recordVideo, boolean screenShots, boolean snapShots, boolean sources, boolean pause) {
        this.notHeadless = notHeadless;
        this.recordVideo = recordVideo;
        this.screenShots = screenShots;
        this.snapShots = snapShots;
        this.sources = sources;
        this.pause = pause;
    }
    public boolean isNotHeadless() {
        return notHeadless;
    }

    public boolean isRecordVideo() {
        return recordVideo;
    }

    public boolean isScreenShots() {
        return screenShots;
    }

    public boolean isSnapShots() {
        return snapShots;
    }

    public boolean isSources() {
        return sources;
    }

    public boolean isPause() {
        return pause;
    }

    @Override
    public String toString() {
        return "PlaywrightConfig{" +
                "notHeadless=" + notHeadless +
                ", recordVideo=" + recordVideo +
                ", screenShots=" + screenShots +
                ", snapShots=" + snapShots +
                ", sources=" + sources +
                ", pause=" + pause +
                '}';
    }
}
