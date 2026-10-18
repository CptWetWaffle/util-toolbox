# Util Toolbox
A toolbox of small utilities missing from RuneLite.

## Tools
- **Mouse Tab Binds**: choose which side panel tab middle click and the extra mouse buttons open.

## Adding a tool
1. Implement `Tool` in `tools/<name>/`, annotate `@Singleton`.
2. Add its toggle and options to `UtilToolboxConfig`.
3. Inject it in `UtilToolboxPlugin` and add it to the `tools` list.
