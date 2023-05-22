This is a repository that contains examples of vulnerable Vert.X code.

The goal is to collect a sufficient amount of security anti-patterns that would be useful enough to be able to build up
rule sets for SAST tools.

The code examples should be:
- As simple as possible, so that they can be easily understood and used as a reference.
- Implemented for versions >= 4.4.x of Vert.X.

# Contributing Code Examples

If you want to contribute a code example, please, try following these guidelines to the extent possible:
* Create an issue describing the anti-pattern that you want to demonstrate, if one does not exist already.
* Create a new class (or classes) that demonstrates the anti-pattern.
* Use the `Insecure` prefix to illustrate the anti-pattern.
* Create a new class (or classes) that demonstrates the secure way of doing the same thing.
* Use the `Secure` prefix to illustrate the correct and secure way.
* Add sufficient comments to the code to explain the anti-pattern and the secure way.
* If necessary, update the Gradle dependencies accordingly.
* If at possible, provide test cases.
