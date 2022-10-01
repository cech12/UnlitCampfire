# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/).

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
