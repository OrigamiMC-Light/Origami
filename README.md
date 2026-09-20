# Origami

A folded, faster fork of [Paper](https://github.com/PaperMC/Paper). Everything Paper
does, with extra optimisations, more configuration, and a wider plugin API — while
staying drop-in compatible with the Paper plugin ecosystem.

Built with [paperweight](https://github.com/PaperMC/paperweight) 2.0. You edit Paper
and Minecraft source directly, then turn your edits into `.patch` files that are
re-applied on top of upstream every build.

## Requirements

- JDK 21+ (the toolchain provisions the exact JDK the build needs automatically)
- Git, configured with a name + email
- ~8 GB free disk and RAM for the first patch/decompile

## First-time setup

1. Configure Git if you haven't:
   ```
   git config --global user.name  "Your Name"
   git config --global user.email "you@example.com"
   ```
2. Apply upstream + Origami patches (downloads and decompiles Paper — slow the first time):
   ```
   ./gradlew applyAllPatches
   ```
   This generates the working trees: `paper-api/`, `paper-server/`, `origami-api/`,
   `origami-server/`.
3. Open the project in IntelliJ IDEA (import the Gradle project).

## Making changes

You have three places to add things, in order of preference:

1. **Plain source (no patch needed).** Add classes under
   `origami-api/src/main/java` or `origami-server/src/main/java`
   (e.g. `dev.origami.*`). Best for anything that doesn't have to modify existing code.
2. **Paper patches.** Edit files inside the generated `paper-api/` or `paper-server/`
   trees, then run `rebuildAllPatches` to capture them under
   `origami-api/paper-patches` / `origami-server/paper-patches`.
3. **Minecraft patches.** Edit decompiled NMS files in `paper-server/`, then rebuild —
   they land in `origami-server/minecraft-patches`.

Regenerate patch files after any edit to the generated trees:
```
./gradlew rebuildAllPatches
```
Commit the `.patch` files (not the generated `paper-*` trees — they're gitignored).

## Building a runnable server jar

```
./gradlew createMojmapPaperclipJar
```
The jar is written to `origami-server/build/libs/`. Run it like any server:
```
java -Xms4G -Xmx4G -jar origami-paperclip-*-mojmap.jar nogui
```
(Accept the EULA in `eula.txt` on first run.)

## Targeting a specific Minecraft version

This repo tracks the Paper commit pinned in `gradle.properties`:

- `paperRef`    — the upstream Paper commit to build on
- `mcVersion` / `apiVersion` — the Minecraft/API version strings
- `channel`     — release channel used in the version name

To move to another version, set `paperRef` to a Paper commit for that version and
match `mcVersion`/`apiVersion`. Use the Paper repository's history/tags as the source
of truth, and keep the `paperweight.patcher` version in `build.gradle.kts` and the
Java toolchain aligned with what that Paper version uses. Note that older releases
(e.g. 1.21.4) predate this paperweight 2.0 layout and need the matching template era.

## CI / releases

- **`.github/workflows/build.yml`** — every push applies patches, builds, and uploads
  the jar as a workflow artifact.
- **`.github/workflows/release.yml`** — pushing a tag like `v1.0.0` builds the jar and
  attaches it to a GitHub Release. Point your website's download button at that release
  asset.

If your Paper version uses a different jar task, change `createMojmapPaperclipJar` in
both workflows accordingly.

## Configuration

Extra tuning lives in `origami.yml` (add the config loader in `origami-server`).
Keep sane defaults so an untouched server behaves exactly like Paper.

## Naming

`group` in `gradle.properties` is `dev.origami` and source packages are `dev.origami.*`.
Change both if you want a different namespace.

## Licensing

Origami is a Paper fork and inherits Paper's licensing. The API portions follow
Paper-API's MIT license; the server portions are **GPL-3.0** (inherited from
CraftBukkit/Spigot/Paper). Do not relicense the server under a permissive license.
