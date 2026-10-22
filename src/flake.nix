{
  description = "DevShell";

  outputs = {flake-parts, ...} @ inputs:
    flake-parts.lib.mkFlake {inherit inputs;} {
      perSystem = {
        system,
        pkgs,
        ...
      }: let
        jdk = pkgs.jdk.override {enableJavaFX = true;};
      in {
        devShells.default = pkgs.mkShellNoCC {
          nativeBuildInputs = with pkgs; [
            python314
            inputs.opencode.packages.${system}.opencode
          ];

          buildInputs = [
            jdk
          ];

          env = {
            JAVA_HOME = "${jdk.home}";
          };
        };
      };

      systems = ["x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin"];
    };

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    opencode.url = "github:anomalyco/opencode/dev";
    flake-parts.url = "github:hercules-ci/flake-parts";
  };
}
