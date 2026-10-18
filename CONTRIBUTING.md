# Contributing

Thanks for helping out with Util Toolbox. This page covers how to report
problems, propose ideas and send changes.

## Before Opening an Issue

Search the open issues first. If someone already reported the same bug
or asked for the same feature, add what you know to that thread instead
of starting a new one.

## Reporting Bugs

Open one issue per bug so each can be tracked and closed on its own.

A report we can reproduce is a report we can fix. Include:
- the plugin and RuneLite versions
- your operating system and version
- numbered steps that trigger the bug
- the behavior you expected and what happened instead
- screenshots or logs, if they help

## Requesting Features

Open one issue per feature. Describe what you are trying to do and why
the feature would help you do it.

## Workflow

This repo is trunk based. `master` is the trunk and always stays in a
working state. Work happens on short-lived branches that merge back
quickly.

History is linear. Merges are fast-forward only, with no merge commits.

```sh
git switch master
git pull --ff-only
git merge --ff-only my-branch
```

If `master` moved while you worked, rebase your branch onto it first.
A fast-forward merge needs your branch to contain the tip of `master`.

```sh
git fetch origin
git rebase origin/master
```

GitHub has no true fast-forward merge button. Use "Rebase and merge" on
a pull request, or merge locally with `--ff-only` and push.

## Sending Changes

- Make sure your branch applies cleanly on top of `master`. If it does
  not, rebase it and update the pull request before asking for review.
- Keep each pull request to a single change. Break larger work into a
  stack (see below).
- Match the style of the surrounding code, including whitespace,
  indentation and naming. Where existing code and the language's usual
  conventions disagree, follow the existing code.
- Add tests for every new feature and bug fix. Cover the behavior you
  changed, including the edge cases.
- Run `./gradlew test` before you open a pull request, and make sure it
  passes. CI runs the same tests on every pull request, and a pull
  request cannot merge while they fail.
- Prefer the standard library, or a few lines of your own code, over a
  new dependency for something simple.
- Keep whitespace-only edits out of commits that change behavior. They
  clutter the diff and slow down review.
- Follow the rules in [AGENTS.md](AGENTS.md). They apply to human
  contributors too.

### Stacked pull requests

Create each branch from the previous one, and point each pull request at
its parent branch with the `gh` CLI:

```sh
git switch -c part-1
# commit work
git push -u origin part-1
gh pr create --base master --title "Add tool interface"

git switch -c part-2
# commit work
git push -u origin part-2
gh pr create --base part-1 --title "Add mouse tab binds tool"
```

Merge from the bottom of the stack up. Once `part-1` lands on `master`,
rebase the next branch onto `master` and retarget its pull request:

```sh
git fetch origin
git rebase --onto origin/master part-1 part-2
git push --force-with-lease
gh pr edit --base master
```

Rebase and merge rewrites commit hashes, so the `--onto` form is needed
to drop the parent commits that were already merged.

## Commits

Commits must be focused. Each one does one thing and leaves the project
building. They do not need to be atomic, but unrelated changes belong in
separate commits. Do not mix reformatting with feature changes.

Write commit messages by these rules:
- Put a blank line between the subject and the body.
- Keep the subject to 50 characters or fewer.
- Start the subject with a capital letter and omit the final period.
- Phrase the subject in the imperative mood.
- Wrap body lines at 72 characters.
- Use the body to say what changed and why, not how.

The subject is declarative and reads as "If applied, this commit will
...". Write `Add mouse tab binds tool`, not `Added mouse tab binds tool`
or `Adding mouse tab binds tool`.

The code shows how a change works. The body says what changed and why it
was needed, including the problem it solves and any trade-offs.

### Example

```text
Open side panel tabs with mouse buttons

F-key binds are hard to reach with one hand on the mouse. Let players
bind middle click and the extra mouse buttons to side panel tabs.

A bound button is consumed, so binding middle click disables camera
rotation on that button. All binds are off by default for that reason.
```

For more on writing good messages, read
[How to Write a Git Commit Message](https://chris.beams.io/git-commit).

## Testing

Run the unit tests with `./gradlew test`. They use JUnit 4 and Mockito,
and live next to the code they cover under `src/test/java`. Test the
logic by mocking `Client` and the other RuneLite services, as the
existing tests do.

Unit tests cannot confirm what the game does, so plugin behavior also
needs an in-game check. State what you tested and how in the pull
request. A clean JVM start is not a passing test.

Run the development client with `./gradlew run`.
