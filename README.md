# Color Changer (Fabric 1.21.2)

A clean-room Fabric client mod project for a grayscale-to-color challenge.

## Intended gameplay rules

- Begin each 30-second cycle in grayscale.
- Restore eight hue-family groups progressively during the cycle.
- Each restored group has a 45% chance to retain its original hue and a 55% chance to receive a randomized hue.
- Both outcomes count as restored color.
- The player dies when restored coverage reaches at least 85%.
- At 30 seconds, the cycle resets to grayscale instantly.
- No interpolation or fade is intended.

## Build

Requires Java 21.

```bash
./gradlew build
```

## Implementation status

This repository currently contains the Gradle/Fabric project scaffold and the isolated progression state machine. The actual full-frame hue-family renderer is not yet implemented. The current eight equal-weight groups are a gameplay approximation, not a measurement of the percentage of colored screen pixels. Do not treat this scaffold as a finished playable release until the renderer is implemented and tested in Minecraft.
