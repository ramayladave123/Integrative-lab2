import java.util.ArrayList;
import java.util.List;
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

        // Create a simple playlist
        Playlist mainPlaylist = new Playlist("My Favorites");
        mainPlaylist.add(new Song(mediaLocation, mediaSource));
        mainPlaylist.add(new Song("NextTrack.mp3", new LocalSource("NextTrack.mp3")));

        Playlist subPlaylist = new Playlist("Top Hits");
        subPlaylist.add(new Song("HitA.mp3", new LocalSource("HitA.mp3")));
        subPlaylist.add(new Song("HitB.mp3", new LocalSource("HitB.mp3")));
        mainPlaylist.add(subPlaylist);

        // Display playlist
        System.out.println("\n--- Playlist Overview ---");
        mainPlaylist.display();

        // Play the playlist
        System.out.println("\n--- Currently Playing ---");
        player.play(mainPlaylist);
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
    void play(Playable playable);
}

class BasicMediaPlayer implements MediaPlayer {
    private Renderer renderer;
    private MediaSource source;

    public BasicMediaPlayer(Renderer renderer, MediaSource source) {
        this.renderer = renderer;
        this.source = source;
    }

    @Override
    public void play(Playable playable) {
        renderer.render("playlist");
        playable.play();
    }
}

abstract class PlayerDecorator implements MediaPlayer {
    protected MediaPlayer decoratedPlayer;

    public PlayerDecorator(MediaPlayer decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override
    public void play(Playable playable) {
        decoratedPlayer.play(playable);
    }
}

class SubtitleDecorator extends PlayerDecorator {
    public SubtitleDecorator(MediaPlayer decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void play(Playable playable) {
        super.play(playable);
        System.out.println("Subtitles activated.");
    }
}

class EqualizerDecorator extends PlayerDecorator {
    public EqualizerDecorator(MediaPlayer decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void play(Playable playable) {
        super.play(playable);
        System.out.println("Equalizer activated.");
    }
}

class WatermarkDecorator extends PlayerDecorator {
    public WatermarkDecorator(MediaPlayer decoratedPlayer) {
        super(decoratedPlayer);
    }

    @Override
    public void play(Playable playable) {
        super.play(playable);
        System.out.println("Watermark activated.");
    }
}

// Composite Pattern: Playable
interface Playable {
    void play();
    void display();
}

class Song implements Playable {
    private String name;
    private MediaSource source;

    public Song(String name, MediaSource source) {
        this.name = name;
        this.source = source;
    }

    @Override
    public void play() {
        source.load();
        System.out.println("Playing track: " + name);
    }

    @Override
    public void display() {
        System.out.println("Track: " + name);
    }
}

class Playlist implements Playable {
    private String name;
    private List<Playable> components = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void add(Playable component) {
        components.add(component);
    }

    @Override
    public void play() {
        System.out.println("Starting playlist: " + name);
        for (Playable component : components) {
            component.play();
        }
    }

    @Override
    public void display() {
        System.out.println("Playlist: " + name);
        for (Playable component : components) {
            component.display();
        }
    }
}