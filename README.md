# Util Toolbox

[![CI](https://github.com/CptWetWaffle/util-toolbox/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/CptWetWaffle/util-toolbox/actions/workflows/ci.yml?query=branch%3Amaster)

A toolbox of small utilities missing from RuneLite.

## Tools
- **Mouse Tab Binds**: choose which side panel tab middle click and the extra mouse buttons open.
- **Low Prayer Flash**: flash the screen when prayer drops below a set threshold.

## Adding a tool
1. Implement `Tool` in `tools/<name>/`, annotate `@Singleton`.
2. Add its toggle and options to `UtilToolboxConfig`.
3. Inject it in `UtilToolboxPlugin` and add it to the `tools` list.
