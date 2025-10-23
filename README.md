# Laboratory 2: Structural Design Pattern  

## Project Overview  
This project refactors a legacy media player into a **Modular Media Streaming Suite** using **Structural Design Patterns** in Java.  
It demonstrates modularity, flexibility, and scalability through:  
- Multiple media sources (Local, HLS, Remote API)  
- Dynamic feature plugins (Subtitles, Equalizer, Watermark)  
- Composite playlists  
- Switching between hardware/software rendering  
- Caching of remote streams  

---

## Implemented Design Patterns  

| Pattern   | Purpose                                      | Example in Code                          |
|-----------|----------------------------------------------|------------------------------------------|
| Adapter   | Unifies different media sources              | `LocalSource`, `HLSAdapter`, `RemoteProxy` |
| Bridge    | Switches between hardware/software rendering | `Renderer`, `HardwareRenderer`, `SoftwareRenderer` |
| Decorator | Adds dynamic features                        | `SubtitleDecorator`, `EqualizerDecorator`, `WatermarkDecorator` |
| Composite | Nested playlists and song hierarchy          | `Playlist`, `Song`                       |
| Proxy     | Caches and reuses remote streams             | `RemoteProxy`, `RemoteSource`            |

---

## Repository Structure  

Integrative2-lab2/
│
├── src/
│ └── Integrative2.java # Main program file
│
├── legacy_code/
│ └── LegacyMediaPlayer.java # Provided legacy version (optional placeholder)
│
├── docs/
│ ├── design_rationale.docx
│ ├── diagram_structural.docx
│ ├── sequence_diagram.docx
│ └── architecture_overview.docx
│
└── README.md

---

# How to Run the Program

1. **Clone the Repository**:  
   - Clone this repository to your local machine:  
     ```bash
     git clone https://github.com/ramayladave123/Integrative-lab2.git
     cd Integrative2-lab2/src
     
2. **Compile the Program**
    -javac Integrative2.java

3. **Run the Program**
    -java Integrative2

4. **Follow the On-Screen Prompts**
    -Choose media source (local, hls, remote)
    -Enter file name, URL, or API endpoint based on the source
    -Select rendering mode (hardware, software)
    -Enable optional features (subtitles, equalizer, watermark)
    -Observe proxy caching behavior when using remote streaming

# Author

    Dave Ramaila
    BSIT
    Integrative Programming 2
    Laboratory 2: Structural Design Pattern

