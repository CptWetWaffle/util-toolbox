# Util Toolbox
A toolbox of small utilities missing from RuneLite.

## Tools
- **Mouse Tab Binds**: open side panel tabs with middle click or extra mouse buttons.

## Adding a tool
1. Implement `Tool` in `tools/<name>/`, annotate `@Singleton`.
2. Add its toggle and options to `UtilToolboxConfig`.
3. Inject it in `UtilToolboxPlugin` and add it to the `tools` list.
