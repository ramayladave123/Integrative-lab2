import java.util.Scanner;

public class Integrative2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter media source type (local, hls, remote): ");
        String sourceType = scanner.nextLine().trim().toLowerCase();

        MediaSource mediaSource = null;
        String mediaLocation = "";

        switch (sourceType) {
            case "local":
                System.out.print("Enter local file name: ");
                mediaLocation = scanner.nextLine().trim();
                mediaSource = new LocalSource(mediaLocation);
                break;
            case "hls":
                System.out.print("Enter HLS stream URL: ");
                mediaLocation = scanner.nextLine().trim();
                mediaSource = new HLSAdapter();
                break;
            case "remote":
                System.out.print("Enter remote API URL: ");
                mediaLocation = scanner.nextLine().trim();
                mediaSource = new RemoteProxy(mediaLocation);
                break;
            default:
                System.out.println("Invalid source type. Exiting.");
                return;
        }

        System.out.print("Use hardware rendering? (yes/no): ");
        boolean useHardware = scanner.nextLine().trim().toLowerCase().equals("yes");
        Renderer renderer = useHardware ? new HardwareRenderer() : new SoftwareRenderer();

        System.out.print("Activate subtitles? (yes/no): ");
        boolean useSubtitles = scanner.nextLine().trim().toLowerCase().equals("yes");

        System.out.print("Activate equalizer? (yes/no): ");
        boolean useEqualizer = scanner.nextLine().trim().toLowerCase().equals("yes");

        System.out.print("Activate watermark? (yes/no): ");
        boolean useWatermark = scanner.nextLine().trim().toLowerCase().equals("yes");

        MediaPlayer player = new BasicMediaPlayer(renderer, mediaSource);
        if (useSubtitles) {
            player = new SubtitleDecorator(player);
        }
        if (useEqualizer) {
            player = new EqualizerDecorator(player);
        }
        if (useWatermark) {
            player = new WatermarkDecorator(player);
        }

        player.play();
    }
}

// Interface for MediaSource
interface MediaSource {
    void load();
}

// LocalSource class
class LocalSource implements MediaSource {
    private String fileName;

    public LocalSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load() {
        System.out.println("Loading local media: " + fileName);
    }
}

// Third-party HLS player (simulated)
class ThirdPartyHLSPlayer {
    public void streamHLS() {
        System.out.println("HLS streaming initiated.");
    }
}

// Adapter for HLS
class HLSAdapter implements MediaSource {
    private ThirdPartyHLSPlayer hlsPlayer = new ThirdPartyHLSPlayer();

    @Override
    public void load() {
        hlsPlayer.streamHLS();
    }
}

// RemoteSource class
class RemoteSource implements MediaSource {
    private String url;

    public RemoteSource(String url) {
        this.url = url;
    }

    @Override
    public void load() {
        System.out.println("Fetching from remote: " + url);
    }
}

// Proxy for remote
class RemoteProxy implements MediaSource {
    private String url;
    private RemoteSource realSource;

    public RemoteProxy(String url) {
        this.url = url;
    }

    @Override
    public void load() {
        if (realSource == null) {
            realSource = new RemoteSource(url);
        }
        System.out.println("Caching remote stream...");
        realSource.load();
    }
}

// Strategy Pattern: Renderer
interface Renderer {
    void render(String mediaName);
}

class HardwareRenderer implements Renderer {
    @Override
    public void render(String mediaName) {
        System.out.println("Hardware rendering for " + mediaName + ".");
    }
}

class SoftwareRenderer implements Renderer {
    @Override
    public void render(String mediaName) {
        System.out.println("Software rendering for " + mediaName + ".");
    }
}

// Decorator Pattern: MediaPlayer
interface MediaPlayer {
    void play();
}

class BasicMediaPlayer implements MediaPlayer {
    private Renderer renderer;
    private MediaSource source;

    public BasicMediaPlayer(Renderer renderer, MediaSource source) {
        this.renderer = renderer;
        this.source = source;
    }

    @Override
    public void play() {
        renderer.render("media");
        source.load();
        System.out.println("Playing media.");
    }
}

abstract class PlayerDecorator implements MediaPlayer {
    protected MediaPlayer decoratedPlayer;

    public PlayerDecorator(MediaPlayer decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override
    public void play() {
        decoratedPlayer.play();
    }
}

class SubtitleDecorator extends PlayerDecorator {
    public SubtitleDecorator(MediaPlayer decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void play() {
        super.play();
        System.out.println("Subtitles activated.");
    }
}

class EqualizerDecorator extends PlayerDecorator {
    public EqualizerDecorator(MediaPlayer decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void play() {
        super.play();
        System.out.println("Equalizer activated.");
    }
}

class WatermarkDecorator extends PlayerDecorator {
    public WatermarkDecorator(MediaPlayer decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void play() {
        super.play();
        System.out.println("Watermark activated.");
    }
}