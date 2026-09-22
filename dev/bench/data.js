window.BENCHMARK_DATA = {
  "lastUpdate": 1790072871795,
  "repoUrl": "https://github.com/JetBrains/intellij-aspect",
  "entries": {
    "Memory Overhead": [
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "94eadc7ba03934d9ee6beb46f9387f1f3a548794",
          "message": "Add CI to measure heap overhead (#211)\n\n...to measure the aspect's overhead against 3 open source projects.",
          "timestamp": "2026-09-11T17:21:44+02:00",
          "tree_id": "262e1d4245322ca3b9e775a92be017a9114bca33",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/94eadc7ba03934d9ee6beb46f9387f1f3a548794"
        },
        "date": 1789141307589,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 121.19815668202764,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 78.87323943661971,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 121.02768887949802,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 258.52895148669796,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 218.21756225425952,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 258.57316911530137,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 113.35740072202165,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "94eadc7ba03934d9ee6beb46f9387f1f3a548794",
          "message": "Add CI to measure heap overhead (#211)\n\n...to measure the aspect's overhead against 3 open source projects.",
          "timestamp": "2026-09-11T17:21:44+02:00",
          "tree_id": "262e1d4245322ca3b9e775a92be017a9114bca33",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/94eadc7ba03934d9ee6beb46f9387f1f3a548794"
        },
        "date": 1789141793625,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 121.19815668202764,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 78.87323943661971,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 121.02768887949802,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 258.52895148669796,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 218.21756225425952,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 258.57316911530137,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 113.35740072202165,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 37.93774319066148,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 35.3125,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 39.56717577816545,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 35.24229074889868,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "49699333+dependabot[bot]@users.noreply.github.com",
            "name": "dependabot[bot]",
            "username": "dependabot[bot]"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "a40efcd5584b41c53a56c5487bb00c38b701a6c0",
          "message": "Bump protobuf from 35.1 to 36.1.bcr.1 (#182)\n\nBumps [protobuf](https://github.com/protocolbuffers/protobuf) from 35.1 to 36.1.bcr.1.\n- [Release notes](https://github.com/protocolbuffers/protobuf/releases)\n- [Commits](https://github.com/protocolbuffers/protobuf/commits)\n\n---\nupdated-dependencies:\n- dependency-name: protobuf\n  dependency-version: '36.0'\n  dependency-type: direct:production\n  update-type: version-update:semver-major\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-14T14:16:20+02:00",
          "tree_id": "08ee77e52442e2123f14913346fc2050d2712a8b",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/a40efcd5584b41c53a56c5487bb00c38b701a6c0"
        },
        "date": 1789389374394,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 119.72477064220183,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 77.96610169491525,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 120.27905884433406,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 102.803738317757,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 258.3463338533541,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 218.717277486911,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 258.5868481023182,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 123.35766423357664,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 38.01169590643275,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 34.639498432601876,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 39.73200075630932,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 33.92070484581498,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "21be2feb5d7b4eee60117c0ce423e56dbf19973b",
          "message": "Update README.md (#215)",
          "timestamp": "2026-09-15T11:34:33+02:00",
          "tree_id": "9cc41dd676875f4b444bec63d88f8ecb6e8035a0",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/21be2feb5d7b4eee60117c0ce423e56dbf19973b"
        },
        "date": 1789465944628,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 119.72477064220183,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 77.96610169491525,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 120.27905884433406,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 102.803738317757,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 258.0343213728549,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 218.1937172774869,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 258.0268686589267,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 106.96864111498259,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 37.93774319066148,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 34.0625,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 39.3781197497047,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 35.24229074889868,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": false,
          "id": "9be9e58f206b66903c727c5687d94ad593f62933",
          "message": "Restrict each targets contribution to the output groups  (#212)\n\nDo not repeat files in the BUILD output group which are already included in the SYNC output group. This can save a measurable amount of memory overhead for specific projects.\n\nFurthermore, each target should only contribute its own required files and not the transitive closure, since the aspect walks the dependencies anyway.",
          "timestamp": "2026-09-15T15:48:44+02:00",
          "tree_id": "ccf17e8fd9ffd907ed71514e0c6ffe650427d2de",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/9be9e58f206b66903c727c5687d94ad593f62933"
        },
        "date": 1789481146428,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.97235023041475,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.49295774647888,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.1965245189566,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 108.65384615384615,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 260.7535321821036,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 220.78947368421052,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 260.9820413298728,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 118.7725631768953,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.89278752436647,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.23270440251572,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.23866434425927,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.130434782608695,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "49699333+dependabot[bot]@users.noreply.github.com",
            "name": "dependabot[bot]",
            "username": "dependabot[bot]"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "7f88744ca311e803806bece59a34efd1f3cac199",
          "message": "Bump rules_scala from 7.2.6 to 7.3.0 (#216)\n\nBumps [rules_scala](https://github.com/bazel-contrib/rules_scala) from 7.2.6 to 7.3.0.\n- [Release notes](https://github.com/bazel-contrib/rules_scala/releases)\n- [Commits](https://github.com/bazel-contrib/rules_scala/compare/v7.2.6...v7.3.0)\n\n---\nupdated-dependencies:\n- dependency-name: rules_scala\n  dependency-version: 7.3.0\n  dependency-type: direct:production\n  update-type: version-update:semver-minor\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-15T17:22:40+02:00",
          "tree_id": "cc31c15f4aa851aea692c80b600059d7235d6bb8",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/7f88744ca311e803806bece59a34efd1f3cac199"
        },
        "date": 1789486148099,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.97235023041475,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.49295774647888,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.1965245189566,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 108.65384615384615,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 260.7535321821036,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 220.78947368421052,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 260.9820413298728,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 118.7725631768953,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.89278752436647,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.23270440251572,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.23866434425927,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.130434782608695,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "f91f7e1bf994091194d70c37d274ef768dbc0e72",
          "message": "Build test fixture with cc_false_toolchain (#214)\n\nSince the llvm toolchain is actually not required for most test fixture builds and adds a lot of IO overhead, test fixture builds can be speed up by using a hermetic stub toolchain.",
          "timestamp": "2026-09-16T08:39:33+02:00",
          "tree_id": "0d1e6a59be93f8d948007ebe39ca11af6ad94ed6",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/f91f7e1bf994091194d70c37d274ef768dbc0e72"
        },
        "date": 1789541030791,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.97235023041475,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.49295774647888,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.1965245189566,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 108.65384615384615,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 260.7535321821036,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 220.78947368421052,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 260.9820413298728,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 118.7725631768953,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "f91f7e1bf994091194d70c37d274ef768dbc0e72",
          "message": "Build test fixture with cc_false_toolchain (#214)\n\nSince the llvm toolchain is actually not required for most test fixture builds and adds a lot of IO overhead, test fixture builds can be speed up by using a hermetic stub toolchain.",
          "timestamp": "2026-09-16T08:39:33+02:00",
          "tree_id": "0d1e6a59be93f8d948007ebe39ca11af6ad94ed6",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/f91f7e1bf994091194d70c37d274ef768dbc0e72"
        },
        "date": 1789543027673,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.97235023041475,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.49295774647888,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.1965245189566,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 108.65384615384615,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 260.7535321821036,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 220.78947368421052,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 260.9820413298728,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 118.7725631768953,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.89278752436647,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.23270440251572,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.23866434425927,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.130434782608695,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "194462+eugenezh@users.noreply.github.com",
            "name": "Evgeny Zhuravlev",
            "username": "eugenezh"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "48ee6b564a97a25360111a669d1f394a198d0fec",
          "message": "Report the Kotlin stdlibs with the source jars their targets declare (#217)\n\n- provide full set of jars in the new KotlinTargetInfo.stdlib_jars attribute (binary- compile- and source- jars); read them from the JavaInfo.java_outputs 'JavaOutput' structure\n- later, on plugin side, for source jars discovery rely on data in stdlib_jars rather than expecting special jars naming (the \"-source\" name suffix)\n- A JavaInfo without java_outputs falls back to the compile jars, as\n  before.",
          "timestamp": "2026-09-16T10:18:09+02:00",
          "tree_id": "b356f14bd86c80834a061b706a0a72a76456b282",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/48ee6b564a97a25360111a669d1f394a198d0fec"
        },
        "date": 1789547757895,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 116.97247706422019,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.05633802816901,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.28084862742925,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 254.57364341085272,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 215.625,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 254.75278259093287,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 125,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.824902723735406,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.81004709576138,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.17057818132157,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.515418502202646,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "0f69e30192ddb87c97da2f9f4b7f853ae010e88d",
          "message": "Add CODEOWNERS file (#218)",
          "timestamp": "2026-09-16T11:45:02+02:00",
          "tree_id": "0a8eeb92fdefe76a8a35e69ac014ab87afe43421",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/0f69e30192ddb87c97da2f9f4b7f853ae010e88d"
        },
        "date": 1789552903092,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.43317972350232,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.12359550561798,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.45122004071527,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 111.53846153846155,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 260.12558869701724,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 218.7418086500655,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 260.3998891692789,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 107.5812274368231,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.69785575048733,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.1875,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.18461357983606,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 31.818181818181817,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "c4eed814065644c75e3152dd188d64e3ea30261c",
          "message": "Add retries to heap analysis builds in CI (#219)\n\nSometimes project builds can fail to fetch a dependency even with the configured repository cache.",
          "timestamp": "2026-09-16T17:17:59+02:00",
          "tree_id": "501b17f7117ef9425544dca99b667a1e718b4967",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/c4eed814065644c75e3152dd188d64e3ea30261c"
        },
        "date": 1789572832071,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 115.52511415525115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 73.74301675977654,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.96348604090349,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 102.803738317757,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 254.88372093023256,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 216.015625,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 255.16852973841316,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 139.71119133574007,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.08771929824561,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 33.22834645669291,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.45298419145088,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.515418502202646,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "klaus.aehlig@jetbrains.com",
            "name": "Klaus Aehlig",
            "username": "aehlig"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "d11bf990e0ad0d0326715cca9c2ca60b239403f5",
          "message": "jvm: Only build non-source resources (#220)",
          "timestamp": "2026-09-18T15:41:50+02:00",
          "tree_id": "09986f5a33890a605a9f00cf5cbd1d82897b2a20",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/d11bf990e0ad0d0326715cca9c2ca60b239403f5"
        },
        "date": 1789739737799,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 116.12903225806453,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.64788732394366,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 115.63171523707858,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 106.38629283489097,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 93.85620915032679,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 106.973942290704,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 95.35714285714286,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.43579766536965,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.28346456692913,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.078055252870165,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.130434782608695,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "2107017bd851bb9feb5307de4b881c6cd0c85a5b",
          "message": "Add nomirror flag to measure tool (#221)\n\n...to deploy the aspect directly into the target project. Avoids unnecessary overhead and fixes issues with some repository rules.",
          "timestamp": "2026-09-21T09:55:46+02:00",
          "tree_id": "b8db4d5546ca49e0deb2a4aff9d13ef868f65878",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/2107017bd851bb9feb5307de4b881c6cd0c85a5b"
        },
        "date": 1789978450188,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 116.74418604651163,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.5042492917847,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.15400447131428,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.7338003502627,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.82997118155619,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.00529196145644,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 87.04453441295547,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.0293542074364,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.23270440251572,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.19813103952098,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.130434782608695,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "klaus.aehlig@jetbrains.com",
            "name": "Klaus Aehlig",
            "username": "aehlig"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "3aa9f404d5c5416a86ca32d466a4690965d8a34f",
          "message": "Extend our performance tests to take different combinations of output groups into account (#222)\n\n* Performance measurement: report the groups requested\n\n* Allow configuring the outputgroups to be measured\n\n* Support heap_analysis tests requesting only some groups\n\n... so that we can more easily identify which part of our aspect\nis causing the overhead. It also allows us to estimate the cost of\nvarious use cases.\n\n* Exted //testing/tests/perf/... tests to full matrix of use cases\n\n... by requesting different output groups. In this way, we can keep\nan overview of how resource usage for those use cases developped\nover time. It also allows us to more easily identify the part of\nour aspect that causes unreasonable overhead.",
          "timestamp": "2026-09-21T12:51:55+02:00",
          "tree_id": "c51d8fc441dee6c40d1ed04b266bf1b1d6791dec",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/3aa9f404d5c5416a86ca32d466a4690965d8a34f"
        },
        "date": 1789988996205,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.42045454545455,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.33900130234012,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.8881118881119,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 97.11815561959655,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.09875912739386,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 102.8,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.833659491193735,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.436708860759495,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.2053432488144,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 35.714285714285715,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "klaus.aehlig@jetbrains.com",
            "name": "Klaus Aehlig",
            "username": "aehlig"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "0df7935c45e8535dcbc5763c7d714dd2b4da466a",
          "message": "Add and use utility functions for restricting to sources or non-sources (#224)\n\n* Add utility functions for more easily constructing depset from sources or non-sources\n\n* Simplify output-group desccription\n\n... using the new utility functions. This change is designed to be\na no-op refactoring; the decission on when to add the sources to the\nSYNC output group or not is left for follow-up discussion and PRs.",
          "timestamp": "2026-09-21T14:45:53+02:00",
          "tree_id": "a819c0a777a068ee60b25d36064f2709e503c3dc",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/0df7935c45e8535dcbc5763c7d714dd2b4da466a"
        },
        "date": 1789995968439,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.22429906542055,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 73.52112676056338,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.438877813615,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 101.92307692307692,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 108.79310344827586,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 94.19263456090651,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 109.2739119670548,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 96,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.0293542074364,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 31.92488262910798,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.1321121583379,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 37.38317757009346,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "9da3e08d83cc6437c6967e108ece330dc8fe77c9",
          "message": "Update Bazel 8 version to 8.8.0 (#223)",
          "timestamp": "2026-09-21T15:40:26+02:00",
          "tree_id": "e252825e46889c9a298861ead1ea4504ade9e180",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/9da3e08d83cc6437c6967e108ece330dc8fe77c9"
        },
        "date": 1789998918709,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.29378531073446,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.03683533614088,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 113.38028169014085,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.96969696969697,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 113.63059346574474,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 98.38056680161942,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.765625,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.18210361067504,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.087560848785316,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 29.130434782608695,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "klaus.aehlig@jetbrains.com",
            "name": "Klaus Aehlig",
            "username": "aehlig"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "dec43890164ae71da3740e92b2a5a19cdfca0d90",
          "message": "Benchmark workflow: include all flavours of syncing (#225)",
          "timestamp": "2026-09-21T15:47:33+02:00",
          "tree_id": "5e9b450b99706d37ac5c9b1597ff300c347e3194",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/dec43890164ae71da3740e92b2a5a19cdfca0d90"
        },
        "date": 1790000005820,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.95348837209302,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.86363636363636,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.50366809260244,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 99.03846153846155,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.454545454545457,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.4775349764468,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 37,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.07082152974505,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.1276629930691,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 113.4020618556701,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.83012259194396,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.24137931034483,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.78804285511416,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 98.38056680161942,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 39.54305799648506,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.429394812680115,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 39.55925606679363,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 41.66666666666667,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 112.28070175438596,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.40287769784173,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.76169484895942,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 104.91803278688525,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.58823529411765,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.94348508634223,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 31.961130361244138,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 28.125,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 16.2426614481409,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 14.533965244865717,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 16.488929110566065,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 11.73913043478261,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.294117647058826,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.6530612244898,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.540450999139715,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 32.158590308370044,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "daniel.brauner@jetbrains.com",
            "name": "Daniel Brauner",
            "username": "LeFrosch"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": false,
          "id": "f7fd500c3648e3d960d6ae7ff2a6a73717044527",
          "message": "Add artifact_location.from_files to convert lists of files (#226)\n\n* Add artifact_location.from_files to convert lists of files\n\n* fixed kotlin_info",
          "timestamp": "2026-09-22T09:28:59+02:00",
          "tree_id": "8c139cad425b6d69c351be9360926d933e9a3c50",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/f7fd500c3648e3d960d6ae7ff2a6a73717044527"
        },
        "date": 1790063028771,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.29545454545455,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.41308176804125,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 104,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.22792022792023,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.2849767962943,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 31.73076923076923,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.22429906542055,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.06837606837607,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.12938487273136,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 113.99999999999999,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.63176265270506,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.52873563218391,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.4222296710835,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 89.60000000000001,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.501742160278745,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.189655172413794,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.67497548005526,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.24305555555556,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.70200573065902,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 110.63676842451432,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 102.8,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.2734375,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 29.58860759493671,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 31.79782803366354,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 31.25,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 15.851272015655576,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.971742543171114,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 15.913128751898848,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 11.894273127753303,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.0293542074364,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 31.39717425431711,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.253657254924626,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 27.82608695652174,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "49699333+dependabot[bot]@users.noreply.github.com",
            "name": "dependabot[bot]",
            "username": "dependabot[bot]"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "afff3b5308862734c4efd6bccd2b59147ac91b0d",
          "message": "Bump buildifier_prebuilt from 8.5.1.4 to 10.0.1 (#229)\n\nBumps [buildifier_prebuilt](https://github.com/keith/buildifier-prebuilt) from 8.5.1.4 to 10.0.1.\n- [Release notes](https://github.com/keith/buildifier-prebuilt/releases)\n- [Commits](https://github.com/keith/buildifier-prebuilt/compare/8.5.1.4...10.0.1)\n\n---\nupdated-dependencies:\n- dependency-name: buildifier_prebuilt\n  dependency-version: 10.0.1\n  dependency-type: direct:production\n  update-type: version-update:semver-major\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-22T09:29:57+02:00",
          "tree_id": "f5480afeb580ab288fc67552dc1b4024b700599c",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/afff3b5308862734c4efd6bccd2b59147ac91b0d"
        },
        "date": 1790063933200,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.29545454545455,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.86549945591788,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 86.91588785046729,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.22792022792023,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.2849767962943,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 31.73076923076923,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.2093023255814,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.13636363636364,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.6214396020834,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 101.92307692307692,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.7328646748682,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.20809248554913,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.1694231075575,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 97.16599190283401,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.67595818815331,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.95128939828081,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.65548578699837,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 35.22267206477733,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.3240418118467,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.55667144906744,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.91860622369589,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 104.0485829959514,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.528375733855185,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 29.11392405063291,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 32.05652785942649,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 26.431718061674008,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 15.851272015655576,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.971742543171114,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 15.913128751898848,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 11.894273127753303,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.765625,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.33908948194662,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.18568009602794,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 31.25,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "49699333+dependabot[bot]@users.noreply.github.com",
            "name": "dependabot[bot]",
            "username": "dependabot[bot]"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "879f9708d01325c3828c28c04bdbd789f12a4df7",
          "message": "Bump protobuf from 36.1.bcr.1 to 36.2 (#231)\n\nBumps [protobuf](https://github.com/protocolbuffers/protobuf) from 36.1.bcr.1 to 36.2.\n- [Release notes](https://github.com/protocolbuffers/protobuf/releases)\n- [Commits](https://github.com/protocolbuffers/protobuf/commits/v36.2)\n\n---\nupdated-dependencies:\n- dependency-name: protobuf\n  dependency-version: '36.2'\n  dependency-type: direct:production\n  update-type: version-update:semver-minor\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-22T12:12:49+02:00",
          "tree_id": "0a61bdf55baef01e86c8e9269b270c9adcad1fcd",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/879f9708d01325c3828c28c04bdbd789f12a4df7"
        },
        "date": 1790072871025,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.95348837209302,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 74.14772727272727,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.18450871041281,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 93.45794392523365,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.81395348837209,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.170454545454543,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.200337462774556,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 34.57943925233645,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.2093023255814,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.32504851287626,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 93.45794392523365,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.46853146853145,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.09770114942529,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.34159422235369,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 36.896551724137936,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 31.48936170212766,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 36.92323097265467,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.43478260869566,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.70815450643777,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 110.90616152129151,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 99.60629921259843,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.919765166340508,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 29.33753943217666,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 32.06958366401763,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 27.75330396475771,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 16.046966731898237,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 14.84992101105845,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 16.27542177028416,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 14.537444933920703,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.765625,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.859399684044234,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.04878022870878,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 24.782608695652176,
            "unit": "%"
          }
        ]
      }
    ]
  }
}