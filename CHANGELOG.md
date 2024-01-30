# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/).

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
