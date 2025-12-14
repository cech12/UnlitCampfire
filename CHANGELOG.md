# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [this versioning scheme](https://gist.github.com/cech12/69319028e88c50349a6b044000a6607b).

## [1.21.11-3.6.0.0] - 2025-12-14
### Changed
- updated to Minecraft 1.21.11 (Fabric 0.139.5+1.21.11, NeoForge 21.11.6-beta, Forge 61.0.2)
- updated Cloth Config support (21.11.151) (Fabric/Quilt)
- updated ModMenu support (17.0.0-alpha.1) (Fabric/Quilt)
- updated Jade support (21.0.1) (Fabric/Quilt/NeoForge)

## [1.21.9-3.5.0.1] - 2025-11-11
### Fixed
- config overrides of Lithium was not correct in NeoForge

## [1.21.9-3.5.0.0] - 2025-10-14
- updated to Minecraft 1.21.9 (Fabric 0.134.0+1.21.9, NeoForge 21.9.15-beta, Forge 59.0.5)
- updated Cloth Config support (20.0.148) (Fabric/Quilt)
- updated ModMenu support (16.0.0-rc.1) (Fabric/Quilt)
- updated Jade support (20.0.5) (Fabric/Quilt/NeoForge)

## [1.21.6-3.4.0.0] - 2025-06-21
### Changed
- updated to Minecraft 1.21.6 (Fabric 0.127.1+1.21.6, NeoForge 21.6.6-beta, Forge 56.0.3)
- updated Cloth Config support (19.0.147) (Fabric/Quilt)
- updated ModMenu support (15.0.0-beta.2) (Fabric/Quilt)
- updated Jade support (19.0.3) (Fabric/Quilt/NeoForge)

## [1.21.5-3.3.0.1] - 2025-06-09
### Fixed
- possible crash caused by incompatibility with Supplementaries mod (thanks to Broom for the report) #63

## [1.21.5-3.3.0.0] - 2025-04-20
### Changed
- updated to Minecraft 1.21.5 (Fabric 0.120.0+1.21.5, NeoForge 21.5.47-beta, Forge 55.0.6)
- updated Cloth Config support (18.0.145) (Fabric/Quilt)
- updated ModMenu support (14.0.0-rc.2) (Fabric/Quilt)
- updated Jade support (18.1.0) (Fabric/Quilt/NeoForge)

### Removed
- campfireDropsItemsWhenUnlitByTimeOrRain and soulCampfireDropsItemsWhenUnlitByTimeOrRain option, because they do not work since MC 1.17 (all loaders)

## [1.21.3-3.2.1.0] - 2025-03-18
### Added
- new configuration options "infiniteCampfireIgnoresRain" and "infiniteSoulCampfireIgnoresRain" to let infinite campfires ignore rain (default: true) (thanks to lolson3 for the PR) #58

## [1.21.3-3.2.0.4] - 2025-01-26
### Fixed
- fixed Fabric build

## [1.21.3-3.2.0.3] - 2024-12-24
### Fixed
- (no changes) second try to upload Fabric to CurseForge and Modrinth.

## [1.21.3-3.2.0.2] - 2024-12-24
### Fixed
- missing language entries for max lit time configurations (Fabric) (thanks to stlouisn for the report) #51

## [1.21.3-3.2.0.1] - 2024-11-28
### Fixed
- incompatibility with Poisonous Potato Update mod which resulted in a startup crash (thanks to CraftyZombie for the report) #50

## [1.21.3-3.2.0.0] - 2024-11-04
### Changed
- updated to Minecraft 1.21.3 (Fabric 0.107.0+1.21.3, Neoforge 21.3.10-beta, Forge 53.0.7)
- updated Cloth Config support (16.0.141) (Fabric/Quilt)
- updated ModMenu support (12.0.0-beta.1) (Fabric/Quilt)
- updated Jade support (16.0.4) (Fabric/Quilt/NeoForge)
- temporary removed The One Probe support until it is ported to 1.21.3 (Neoforge)

## [1.21-3.1.1.4] - 2024-11-03
### Fixed
- incompatibility with NeoForge version of Lithium 0.14.0-beta

## [1.21-3.1.1.3] - 2024-11-03
### Fixed
- incompatibility with Lithium 0.14.0-beta (thanks to Kloytz for the report) #49

## [1.21-3.1.1.2] - 2024-09-28
### Fixed
- incompatibility with Canary block ticking optimization (deactivated via canary.overrides.properties) (thanks to CapoFantasma97 for the report) #46

## [1.21-3.1.1.1] - 2024-09-04
### Fixed
- the lower light level of the (soul) campfire was not removed when burnables were added

## [1.21-3.1.1.0] - 2024-09-04
### Added
- new behaviour that a (soul) campfire decreases the light level for the last 30 seconds (configurable, can be deactivated) (all loaders) (thanks to Acenthus for the idea) #38
- new configuration options "campfireMaxLitTimeExtension" and "soulCampfireMaxLitTimeExtension" to extend the burn time beyond the normal burn time when adding burnables (all loaders) (thanks to Fer0x453 for the idea) #42

### Changed
- number config options are now text fields instead of sliders (Fabric)

## [1.21-3.1.0.1] - 2024-07-18
### Fixed
- (soul) campfires refused to go out by rain when they were configured to burn infinitely (burn time = 0) (all loaders) (thanks to Enchilada for the report) #40

## [1.21-3.1.0.0] - 2024-07-14
### Changed
- updated NeoForge to 21.0.94-beta
- the `config` directory is used for the default configuration (NeoForge)

### Fixed
- crashed on startup with NeoForge (caused by a breaking change in 21.0.82-beta)

## [1.21-3.0.0.0] - 2024-06-23
### Changed
- Updated to Minecraft 1.21 (Fabric 0.100.3+1.21, Neoforge 21.0.29-beta, Forge 51.0.17)
- Updated Cloth Config support (15.0.127) (Fabric/Quilt)
- Updated ModMenu support (11.0.1) (Fabric/Quilt)
- updated Jade support (Fabric 15.0.4, NeoForge 15.0.5)
- Updated The One Probe support (Neoforge 1.21_neo-12.0.0)

## [1.20.6-2.5.0.1] - 2024-06-06
### Fixed
- incompatibility with Lithium block ticking optimization (Fabric/Quilt) (deactivated via fabric.mod.json) (thanks to Anonyku05 for the report) #36

## [1.20.6-2.5.0.0] - 2024-06-04
### Changed
- updated NeoForge to 20.6.75-beta to support Jade
- updated Jade support (Fabric 14.2.4)
- re-added Jade support for NeoForge (NeoForge 14.2.3)

## [1.20.6-2.4.0.2] - 2024-05-14
### Fixed
- known issue fixed: Crash in Forge during world loading/creation (Thanks to Sweek9 for the report) #35

## [1.20.6-2.4.0.1] - 2024-05-12
### Fixed
- Forge does not crash at startup any longer
- fixed untranslated item tag warning in logs (Fabric/Quilt)

### Known issues
- Mixin issues in Forge at world loading (Fabric, Quilt & NeoForge are working great!)

## [1.20.6-2.4.0.0] - 2024-05-03
### Changed
- Updated to Minecraft 1.20.6 (Fabric 0.97.8+1.20.6, Neoforge 20.6.22-beta, Forge 50.0.4)
- Updated Cloth Config support (Fabric 14.0.126)
- Updated ModMenu support (Fabric 10.0.0-beta.1)
- Updated Jade support (Fabric 14.1.0)
- Updated The One Probe support (Neoforge 1.20.5_neo-11.1.1)

### Removed
- Temporary removed Jade support for Forge and Neoforge (actually not available for 1.20.6)

## [1.20.4-2.3.0.0] - 2024-04-13
### Added
- add Fabric (>=0.96.11+1.20.4) support (Fabric, Quilt)

## [1.20.4-2.2.0.0] - 2024-04-08
### Changed
- updated Neoforge to 20.4.225
- updated Jade support for Neoforge to 1.20.4-13.3.1
- re-added The One Probe (1.20.4_neo-11.0.2) support (Neoforge)

## [1.20.4-2.1.0.2] - 2024-01-30
### Fixed
- Naturally generated campfires could crash the game #31 (thanks to hanweiyyds for the report)

## [1.20.4-2.1.0.1] - 2024-01-19
### Fixed
- Startup crash in Forge (thanks to solitarybear)

## [1.20.4-2.1.0.0] - 2024-01-17
### Changed
- Update to Minecraft 1.20.4 (Forge 49.0.19, Neoforge 20.4.69-beta)
- updated compat with Jade to 1.20.4-13.2.1+

## [1.20.2-2.0.0.0] - 2024-01-17
### Changed
- Move to Multiloader mod template to support Forge and Neoforge
- Update to Minecraft 1.20.2 (Forge 48.1.0, Neoforge 20.2.86)
- updated compat with Jade to 1.20.2-12.3.0+
- temporary deactivate The One Probe support until the mod is ported to 1.20.2

## [1.20.1-1.9.0.1] - 2023-08-22
### Fixed
- wooden shovel was used as burnable instead of extinguishing the campfire (thanks to brass_mccrafty for the report) #27
- resetting the burn and rain timer was incorrect when campfire was extinguished by shovel, water bucket or water splash potion

## [1.20.1-1.9.0.0] - 2023-08-09
### Changed
- changed Forge to NeoForge 1.20.1-47.1.54 (compatible with Forge 47.1.0)
- updated compat with Jade to 1.20.1-11.4.3
- updated compat with The One Probe to 1.20.1-10.0.1

## [1.20-1.8.1.0] - 2023-06-12
### Changed
- re-add Jade (1.20-11.0.3) support #24
- re-add The One Probe (1.20.0-9.0.0) support #24

## [1.20-1.8.0.0] - 2023-06-08
### Changed
- Updated mod to Forge 1.20-46.0.1 #23
- temporary deactivate Jade & The One Probe support until the mods are ported to 1.20

## [1.19-1.7.1.0] - 2023-05-24
### Added
- Added support for Jade mod (thanks to hipsterjazzbo for your PR) #22

### Changed
- Overhauled The One Probe support 

### Removed
- pt_br and pt_pt translation because all texts were changed (new translations are welcome)

## [1.19-1.7.0.1] - 2023-02-05
### Added
- Added pt_br and pt_pt translation #21 (thanks to sanduicheirainox)

## [1.19-1.7.0.0] - 2022-10-01
### Added
- Combustible/Burnable items can be added to (soul) campfire to extend the burning time (configurable) (thanks to elexblue84 for the idea) #6
- Option to make the lit time of (soul) campfires be affected by sleep time (default: deactivated) (thanks to Toast-Bucket for the idea) #16
- Item tag "unlitcampfire:makes_campfire_infinite", which defines items that can make lit campfires burn infinitely (default: magma cream)
- Option to let generated (soul) campfires burn infinitely (default: true) (thanks to Legomastar for the idea) #12

### Fixed
- rain timer reset was not reliable

## [1.19-1.6.2.0] - 2022-09-20
### Added
- The One Probe support - Show lit time of campfires

### Changed
- Updated mod to Forge 1.19-41.0.96

## [1.19-1.6.1.0] - 2022-09-01
### Changed
- max limit increased of following configurations: campfireLitTime, campfireRainUnlitTime, soulCampfireLitTime, soulCampfireRainUnlitTime #17 (thanks to serialtasted for the report)

## [1.19-1.6.0.0] - 2022-07-06
### Changed
- Updated mod to Forge 1.19-41.0.62 #13

## [1.18.1-1.5.0.0] - 2022-04-30
### Changed
- Update mod to Forge 1.18.1-39.0.0 (fix Log4J security issue)

### Fixed
- re-igniting the campfire didn't work

## [1.18-1.4.0.0] - 2021-12-04
### Changed
- Update mod to Forge 1.18-38.0.6

## [1.17.1-1.4.0.0] - 2021-09-14
### Changed
- Update mod to Forge 1.17.1-37.0.59
- changed versioning to fit [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/)

## [1.3.0_1.16] - 2021-04-06
### Added
- 2 new configs per campfire added #4 (thanks to Misticblade7 for the idea)

### Changed
- (soul)CampfireRainUnlitTime: time a campfire burns until it goes out during rain
- (soul)CampfireRainParticleFactor: factor of particle count of a campfire during rain

### Removed
- 1 config per campfire removed: (soul)UnlitCampfireWithRain: because (soul)CampfireRainUnlitTime contains a corresponding value (-1)

## [1.2.0_1.16] - 2021-02-13
### Changed
- Separate soul campfire options from campfire options.

### Fixed
- Bugfix: Destroying campfire failed during rain.

## [1.1.0_1.16] - 2020-12-01
### Added
- server config file added

### Changed
- a campfire is lit for 2000 ticks (configurable) until it goes out by itself (can be deactivated)
- a campfires goes out when rain falls on it (configurable)
- contained items are dropped off the campfire when it goes out by itself or by rain (configurable)
- a campfire can be configured to be destroyed after it goes out by itself (default disabled)

## [1.0.0_1.16] - 2020-11-04
### Added
- set the default state of a campfire to unlit
