# Creating a desktop app with Atom Shell and ClojureScript

[Atom Shell](https://github.com/atom/atom-shell) gives you a way to
create a desktop(ish) web application which can access your local OS via
the Node.js environment and the npm module ecosystem.

This working example presents a way to use all this power with
ClojureScript.

There's some Javascript left: ``bootstrap.js``. This file doesn't
require much changes so I see no problem with leaving it as it is.

The rest of the stuff is ClojureScript, and it's in
``src/example/core.js``. The UI (not much of that :)) uses [Reagent](https://github.com/reagent-project/reagent).

There are two types of dependencies here:

* Node modules (installed via apm)
* ClojureScript modules (installed via leiningen)

Using Reagent to render HTML demonstrates that leiningen dependencies
are working, and using the [walk](https://www.npmjs.com/package/walk)
module shows how to use npm modules.

## Installing Atom Shell

You can get pre-built binaries [from here](https://github.com/atom/atom-shell/releases).

## Installing native node modules

[To depend on native Node modules](https://github.com/atom/atom-shell/blob/master/docs/tutorial/using-native-node-modules.md)
you should use [apm](https://github.com/atom/apm). How do you install
the apm package manager? Well, with npm package manager of course :)
``npm install apm``
(I used the npm 2.7.1 which comes with io.js 1.6.1)

In our example we depend on the native node module "walk", so let's
install it locally to our Atom Shell application directory:

```apm install .```

Note: do not run apm install <package name>, it will not work for Atom
Shell app.

## Build the app

You need leiningen. Get it from [here](http://leiningen.org/)

Fetch dependencies:

```lein deps```

To build once, run:

```lein cljsbuild once```

To build automatically, run:

```lein cljsbuild auto```

Sometimes you will get nasty "Uncaught Error: goog.require could not
find: example.core" errors. Try to delete everything under out/
directory manually and rebuild. Leiningen's clean doesn't do the trick
for some reason.

## Run the app

cd to this checkout directory, and run Atom in there:

```/Applications/Atom.app/Contents/MacOS/Atom .```

(the path to Atom is specific to your OS and Atom installation of course)

## REPL

There are a few ways to create REPLs with ClojureScript. Unfortunately,
almost all of them require running our generated JavaScript via a page
served by a web server. With Atom
Shell, there's no web server. We are just browsing local files
via ``"file://"`` [Here](https://github.com/emezeske/lein-cljsbuild/blob/1.0.5/doc/REPL.md)
we can see multiple ways to connect a REPL to browser. The only one of
these that works somewhat is the Rhino one:

```lein trampoline cljsbuild repl-rhino```

And that can't run our Node environment properly. We can't, for example,
require a node module and use it since we are inside Rhino/JVM.
