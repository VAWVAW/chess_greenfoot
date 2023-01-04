{
  description = "A very basic flake for rust development";

  inputs = {
    flake-utils.url = "github:numtide/flake-utils";
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-22.05";
    greenfoot-sources.url = "github:Rc-Cookie/greenfoot-sources-combined";
    greenfoot-sources.flake = false;
  };

  outputs = { self, nixpkgs, flake-utils, greenfoot-sources }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
        lib = pkgs.lib;
#        greenfoot = (pkgs.greenfoot.overrideAttrs (finalAttrs: prevAttrs: {
#          installPhase = builtins.concatStringsSep "\n" (builtins.filter
#            (e: !lib.hasInfix "rm -r $out/share/greenfoot/j" e)
#            (lib.splitString "\n" prevAttrs.installPhase));
#          }));
        greenfoot = pkgs.greenfoot.overrideAttrs (finalAttrs: prevAttrs: {
          installPhase = ''
              mkdir -p $out
              cp -r usr/* $out
              makeWrapper ${pkgs.openjdk}/bin/java $out/share/greenfoot/jdk/bin/java
              makeWrapper ${pkgs.openjdk}/bin/java $out/bin/greenfoot \
                --add-flags "-Djavafx.embed.singleThread=true -Dawt.useSystemAAFontSettings=on -Xmx512M -cp \"$out/share/greenfoot/bluej.jar\" bluej.Boot -greenfoot=true -bluej.compiler.showunchecked=false -greenfoot.scenarios=$out/share/doc/Greenfoot/scenarios -greenfoot.url.javadoc=file://$out/share/doc/Greenfoot/API"
            '';
        });
      in with pkgs; rec {
        devShell = mkShell rec {
          buildInputs = [
            greenfoot
          ];

          shellHook = ''
            tmp_dir=/tmp/greenfoot

            function cleanup() {
              rm -r $tmp_dir
            }

            trap cleanup EXIT

            mkdir $tmp_dir
            ln -s ${greenfoot}/share/greenfoot $tmp_dir/greenfoot-lib
            ln -s ${greenfoot-sources} $tmp_dir/greenfoot-sources
          '';
        };
    });
}
