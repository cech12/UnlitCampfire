# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/).

## [1.16.5-1.4.0.0] - 2021-09-??
### Changed
- Update mod to Forge 1.17.1-37.0.46
- changed versioning to fit [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/)
- ...

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