import { build } from "esbuild";
import { join } from "path";
import { readFileSync } from "fs";

const pkg = JSON.parse(
  readFileSync(join(process.cwd(), "package.json"), "utf8")
);

const options = {
  entryPoints: ["guest-js/index.ts"],
  bundle: true,
  platform: "node",
  target: "es2019",
  minify: true,
  sourcemap: false,
  plugins: [
    {
      name: 'externalize-deps',
      setup(build) {
        build.onResolve({ filter: /^@tauri-apps\/api/ }, args => ({ path: args.path, external: true }));
        build.onResolve({ filter: /.*/ }, args => {
          if (Object.keys(pkg.dependencies || {}).includes(args.path) || Object.keys(pkg.peerDependencies || {}).includes(args.path)) {
            return { path: args.path, external: true };
          }
        });
      },
    },
  ],
};

// Generate ES Module
build({
  ...options,
  format: 'esm',
  outfile: pkg.exports.import,
}).catch(() => process.exit(1));

// Generate CommonJS
build({
  ...options,
  format: 'cjs',
  outfile: pkg.exports.require,
}).catch(() => process.exit(1));