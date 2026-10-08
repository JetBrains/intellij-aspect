window.BENCHMARK_DATA = {
  "lastUpdate": 1791444479187,
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
          "id": "757f4fa509772fd4dec4339b8ba90dc9400e3c9e",
          "message": "Protobuf: increase range of tested versions (#234)",
          "timestamp": "2026-09-22T16:39:51+02:00",
          "tree_id": "761d50c92e8dbe1885b1b86c3f93e4022e08927e",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/757f4fa509772fd4dec4339b8ba90dc9400e3c9e"
        },
        "date": 1790089059434,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.34883720930233,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.57954545454545,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.4805430974609,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 105.76923076923077,
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
            "value": 117.75700934579439,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.56818181818183,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.96770412986773,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 66.66666666666666,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.90861159929702,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.4971098265896,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.43625048440857,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 97.6,
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
            "value": 110.39861351819758,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.58404558404558,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.30318019760705,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 104,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.784313725490197,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 29.08805031446541,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 32.15549825110206,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25,
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
            "value": 35.0293542074364,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.70142180094787,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.355759551951024,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 32.589285714285715,
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
          "id": "40124a44177192341bd53c4f2fc3015bbf19303b",
          "message": "Document output groups in README (#236)",
          "timestamp": "2026-09-22T16:49:31+02:00",
          "tree_id": "5663fd663a53e472b367215729eaf8d619f6b435",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/40124a44177192341bd53c4f2fc3015bbf19303b"
        },
        "date": 1790090039237,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.95327102803739,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.29545454545455,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.03482757960121,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 95.1923076923077,
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
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.64788732394366,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.65826723004209,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 99.03846153846155,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 108.27464788732395,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.36219336219335,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.7541511574402,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 98.8,
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
            "value": 112.43432574430823,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 97.12230215827337,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 113.15943919157503,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 104,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.724070450097845,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.683385579937305,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 31.82108324258566,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 32.589285714285715,
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
            "value": 35.0293542074364,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 31.240188383045524,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.01547367727835,
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
          "id": "99f05f432bbf831beea5028e8a5cfe60bfd74f57",
          "message": "Switch to larger Windows runner (#235)",
          "timestamp": "2026-09-22T18:26:52+02:00",
          "tree_id": "1e59c2e1d6ad8dc24e6db471967381c15e9bd439",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/99f05f432bbf831beea5028e8a5cfe60bfd74f57"
        },
        "date": 1790095537338,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.48837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.08781869688386,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.43082325820748,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 100,
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
            "value": 116.74418604651163,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.3541076487252,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.86988740391466,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 88.18181818181819,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.7391304347826,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.83381088825216,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.35573038929829,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 96.8503937007874,
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
            "value": 111.34380453752182,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.40287769784173,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.69042213586184,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 100.4,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.724070450097845,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.930817610062892,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 31.89525426040048,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 29.464285714285715,
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
            "value": 34.24657534246575,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 33.22784810126582,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.228461989705565,
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
          "id": "778c9c729427ebb6c6e5a8937d2cde013f9d7c98",
          "message": "Bump rules_kotlin from 2.4.10 to 2.4.20 (#232)\n\nBumps [rules_kotlin](https://github.com/bazel-contrib/rules_kotlin) from 2.4.10 to 2.4.20.\n- [Release notes](https://github.com/bazel-contrib/rules_kotlin/releases)\n- [Changelog](https://github.com/bazel-contrib/rules_kotlin/blob/master/CHANGELOG.md)\n- [Commits](https://github.com/bazel-contrib/rules_kotlin/compare/v2.4.10...v2.4.20)\n\n---\nupdated-dependencies:\n- dependency-name: rules_kotlin\n  dependency-version: 2.4.20\n  dependency-type: direct:production\n  update-type: version-update:semver-patch\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-23T09:41:50+02:00",
          "tree_id": "1b4947d2c8a364e7f32410c4a078be51ebf554a4",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/778c9c729427ebb6c6e5a8937d2cde013f9d7c98"
        },
        "date": 1790150504991,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.41860465116278,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 74.43181818181817,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.52314779926091,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.113314447592067,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.448589362781874,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 34.61538461538461,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.13636363636364,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.45044019500115,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 84.21052631578947,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.54385964912281,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.65129682997119,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.49291235921208,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 98.8,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 40.316901408450704,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.71757925072046,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 40.047608704350054,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 37.6,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.99476439790577,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.12068965517241,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.23487171546714,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 99.60629921259843,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.078125,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.482003129890455,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 31.97209544145657,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 16.2426614481409,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 14.98422712933754,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 16.271198640312853,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 10.13215859030837,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.22504892367906,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.23270440251572,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.17973343131336,
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
          "id": "3e10b209cc4bb61e82dc28e99c6fa69d87ef70c1",
          "message": "Add buildbarn-remote-execution as performance test project (#237)\n\n* Add buildbarn remote execution as a performance test project\n\n... to have at least one (albeit quite small) real-world go project\nwe measure the overhead of our setup.\n\n* Include bbremote in our routine perf measurements",
          "timestamp": "2026-09-23T15:04:22+02:00",
          "tree_id": "f1f15595b0efd72017586b7ea2cc2502b082cb70",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/3e10b209cc4bb61e82dc28e99c6fa69d87ef70c1"
        },
        "date": 1790169849641,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.41860465116278,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.37110481586402,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.85549881676,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 107,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.113314447592067,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.448589362781874,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 34.61538461538461,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.22429906542055,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.3541076487252,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.67334100906899,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 105.76923076923077,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.175675675675674,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.281646427832314,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 11.494252873563218,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.117647058823529,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.427609427609427,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.461340639534189,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 11.494252873563218,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.810650887573964,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.202702702702704,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.69828973219313,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.74912891986064,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.82209469153516,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.12414102776857,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 92.91338582677166,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 40.316901408450704,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.71757925072046,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 40.047608704350054,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 37.6,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.13043478260869,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.99427753934192,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.38987374331202,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 106.55737704918033,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.528375733855185,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.94348508634223,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 31.932475867135203,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 30.454545454545457,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 16.2426614481409,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 14.98422712933754,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 16.271198640312853,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 10.13215859030837,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 34.90196078431372,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 31.60377358490566,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.348148331698624,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 32.589285714285715,
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
          "id": "d215371c65c106f6fdb571ad0fff3a8f03fc15bd",
          "message": "Bump rules_cc from 0.2.22 to 0.2.25 (#230)\n\nBumps [rules_cc](https://github.com/bazelbuild/rules_cc) from 0.2.22 to 0.2.25.\n- [Release notes](https://github.com/bazelbuild/rules_cc/releases)\n- [Commits](https://github.com/bazelbuild/rules_cc/compare/0.2.22...0.2.25)\n\n---\nupdated-dependencies:\n- dependency-name: rules_cc\n  dependency-version: 0.2.25\n  dependency-type: direct:production\n  update-type: version-update:semver-patch\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-24T07:59:35+02:00",
          "tree_id": "95de501dd84af6ae758f7801282b7cdfb8f9da9e",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/d215371c65c106f6fdb571ad0fff3a8f03fc15bd"
        },
        "date": 1790230587305,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 74.43181818181817,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.85911623437384,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 110.00000000000001,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.113314447592067,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.448589362781874,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 34.61538461538461,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.13636363636364,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.56229251964578,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 113.99999999999999,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.175675675675674,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.33564323847336,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 4.444444444444445,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.117647058823529,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.427609427609427,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.461340639534189,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 11.494252873563218,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.647058823529413,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.151515151515152,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.3365468060406,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 18.88888888888889,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.74912891986064,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.27324749642347,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.24653804699066,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 90.6614785992218,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 40.316901408450704,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.71757925072046,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 40.047608704350054,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 37.6,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.3240418118467,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.4131994261119,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.59515757625209,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 102.42914979757086,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.784313725490197,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.57142857142857,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 32.10630454711231,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 29.515418502202646,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 16.2426614481409,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 14.98422712933754,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 16.271198640312853,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 10.13215859030837,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.09803921568627,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 33.175355450236964,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.471084686518175,
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
          "id": "839c90db3e6ca8332d475aa300367403be1377df",
          "message": "Pad partial versions (#233)\n\n...such as \"8\" to a full (major, minor, patch) triple. Starlark compares tuples element-wise and treats a shorter prefix as smaller, so (8,) < (8, 0, 0) would hold.",
          "timestamp": "2026-09-24T07:59:50+02:00",
          "tree_id": "ce361e690b8b2c67679859ca95f750c56aa07eda",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/839c90db3e6ca8332d475aa300367403be1377df"
        },
        "date": 1790231650667,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.95348837209302,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.93767705382436,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.73409849185173,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 93.45794392523365,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 19.43661971830986,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.38103448868569,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 38.46153846153847,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.20396600566572,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.94988652620427,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.513513513513514,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.477449681033416,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 10.638297872340425,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.792899408284024,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.16949152542373,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.522806403662342,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.218934911242602,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.202702702702704,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 27.70041205151331,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 14.942528735632186,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.18038528896672,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.36219336219335,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.79345800169634,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 94.8,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.501742160278745,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.95128939828081,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.44654697834837,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 31.1284046692607,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.14982578397212,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.4080459770115,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.68589777084621,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 102.42914979757086,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 30.980392156862745,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 29.58860759493671,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 32.16913755203032,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25.11013215859031,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 16.2426614481409,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 14.960629921259844,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 16.33449062126525,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 8.81057268722467,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 35.09803921568627,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 32.18210361067504,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 36.404635563080376,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 32.17391304347826,
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
          "id": "c1e80e4417663cc9821561294a5199f2649135be",
          "message": "Bump minimum Bazel version for toolchains aspects to 9 (#238)\n\n...due to native crashes caused by toolchains aspects on Bazel 8.",
          "timestamp": "2026-09-24T09:41:46+02:00",
          "tree_id": "2b13fad71f41ccd158b7af542988bf6b48c803d6",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/c1e80e4417663cc9821561294a5199f2649135be"
        },
        "date": 1790236771713,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.95327102803739,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 74.14772727272727,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.0943179849409,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 81.81818181818183,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 116.74418604651163,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.06837606837607,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.87200754972224,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 94.54545454545455,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.256695909197916,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 11.11111111111111,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.472972972972974,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.505831723615618,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 19.54022988505747,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 19.19191919191919,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.537317407440078,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 19.54022988505747,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.28272251308901,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.96556671449068,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.68039496793449,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 88.66396761133603,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 39.473684210526315,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.38129496402878,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 39.69168101435023,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.82024432809774,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.41547277936962,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.16406557818269,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 86.4,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.57142857142857,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 26.447574334898277,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 29.832366902178777,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 26.431718061674008,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.090019569471623,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.695924764890282,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.205328438651978,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.392857142857142,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 33.07240704500978,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 30.51643192488263,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 34.34579728077708,
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
          "id": "3869f11560063de9b6621e1483e9b7fc923592ac",
          "message": "Update and pin Maven dependencies (#240)",
          "timestamp": "2026-09-24T11:22:30+02:00",
          "tree_id": "2f13fa7555806a81b807769ee44944565703c315",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/3869f11560063de9b6621e1483e9b7fc923592ac"
        },
        "date": 1790242756589,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.95348837209302,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 72.3943661971831,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.49701631622371,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 88.18181818181819,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.81395348837209,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 19.8300283286119,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.19827340525659,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 30.8411214953271,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.69158878504673,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.42045454545455,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.65825113468875,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.457912457912458,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.483620650702644,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 26.666666666666668,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.121621621621621,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.215876560215927,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 3.1914893617021276,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 14.76510067114094,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 29.005594210475984,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 10.638297872340425,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.28272251308901,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.1090387374462,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.63634097124304,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 101.21457489878543,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 39.54305799648506,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.910533910533914,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 39.632758356914486,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 32.677165354330704,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.36363636363636,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.83931133428982,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.56202703669005,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 99.60629921259843,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.3203125,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 26.76056338028169,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 29.930016796905807,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 26.431718061674008,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.50980392156863,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.539184952978054,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.420349682825737,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 10.13215859030837,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 32.68101761252446,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 30.250783699059564,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 34.032218319032815,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 26.785714285714285,
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
          "id": "1da36626b4df4d195968ded5c9753aa327676f86",
          "message": "config: do not mix tabs and spaces (#242)\n\nInstead indent everything with two spaces.",
          "timestamp": "2026-09-25T10:01:10+02:00",
          "tree_id": "6e33c703afa5e73b2963921b404a72665b657142",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/1da36626b4df4d195968ded5c9753aa327676f86"
        },
        "date": 1790324438443,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.88888888888889,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.37110481586402,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.55887322938523,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 107,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.113314447592067,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.278033795564966,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 30.8411214953271,
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
            "value": 116.87602574128866,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.076923076923077,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.175675675675674,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.52361695187026,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 11.494252873563218,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.117647058823529,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.472972972972974,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.477650731452787,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.810650887573964,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 14.915254237288137,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.80461405173979,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 25,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.44599303135888,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.67241379310344,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.9388944516645,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 96.8503937007874,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 39.64912280701755,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 34.104046242774565,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 39.656959355703634,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 39.34426229508197,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.69284467713787,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 93.96551724137932,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.12365503792255,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 88.25910931174089,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.767123287671232,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.322784810126585,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.283799142461426,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25.90909090909091,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.117647058823529,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.71585557299843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.361521591694693,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.392857142857142,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 32.87671232876712,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 30.50314465408805,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 34.35492574325196,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 30.837004405286343,
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
          "id": "5b08cdf0f02b0a6d4eae9bc96cc7b64a4ac3cd48",
          "message": "Add String value for the name of the only aspect (#241)\n\nFor performance reasons, we will not switch back to using many apsects.\nTherefore, export a value for the only aspect to be used, allowing a slow\nmigration to a less involved API (#187).",
          "timestamp": "2026-09-25T12:58:57+02:00",
          "tree_id": "b1a134d562ab2ffa9e04368f80c2c1a5824067d4",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/5b08cdf0f02b0a6d4eae9bc96cc7b64a4ac3cd48"
        },
        "date": 1790334974798,
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
            "value": 113.89661821719454,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.454545454545457,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.473105873343854,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 40,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.2093023255814,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.85227272727273,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.56955099235897,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 90.35087719298247,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 14.285714285714285,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.574992369304212,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 11.11111111111111,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.764309764309765,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 13.847112552963504,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 3.1914893617021276,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.647058823529413,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 14.527027027027026,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.91113730570144,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 15.555555555555555,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.37261698440207,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.44079885877318,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 105.79420948813194,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 98.38056680161942,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.501742160278745,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.664756446991404,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.46582579395268,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 113.88400702987698,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 97.83549783549783,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 114.34122396379874,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 101.6,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.57142857142857,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 26.687598116169546,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.032745916939145,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 22.026431718061673,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.090019569471623,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.71585557299843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.27169461137017,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 15.454545454545453,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 33.13725490196078,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 31.230283911671926,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 34.26197378179451,
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
          "id": "94d2c1116f1e2b10df0d0e4d2a18e9f2f4e90ce0",
          "message": "Update latest dependencies used for testing (#239)",
          "timestamp": "2026-09-25T14:58:31+02:00",
          "tree_id": "e658cdfb1148743e34ec16aba5a5b085fccb4019",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/94d2c1116f1e2b10df0d0e4d2a18e9f2f4e90ce0"
        },
        "date": 1790342274768,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.23943661971832,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.88606905976712,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 93.45794392523365,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 19.101123595505616,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.46974038089652,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 38.46153846153847,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.75700934579439,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.56818181818183,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.73060406967075,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 79.48717948717949,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.175675675675674,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.204837884657852,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.792899408284024,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.135135135135135,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.312617222947873,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 11.494252873563218,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 19.594594594594593,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.720227888898776,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 19.54022988505747,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.70577933450087,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.95977011494253,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.28432418108878,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 94.8,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.917975567190226,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.33333333333333,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.73269338817302,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 34.8,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.36363636363636,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.12068965517241,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.72515903178922,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 101.6,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.627450980392155,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 26.687598116169546,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.50209505292294,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 29.09090909090909,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.285714285714285,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.695924764890282,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.34673904619771,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 10.13215859030837,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 33.33333333333333,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 30.660377358490564,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 34.40987092951949,
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
          "id": "10c691ca7060892a03712a11f825307072b6a817",
          "message": "Collect all_files from cc toolchains  (#227)\n\nCollects files report by the cc toolchain to the appropriate output groups and serializes them to CToolchainIdeInfo.all_files, such that they can be processed by the plugin later on.",
          "timestamp": "2026-09-28T14:25:52+02:00",
          "tree_id": "7ed2039a6f10f4ca25952abe7ade3f212cf0d2ee",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/10c691ca7060892a03712a11f825307072b6a817"
        },
        "date": 1790599557223,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.48598130841121,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 72.52124645892351,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.86790242944586,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 110.00000000000001,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 36.27906976744186,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 19.8300283286119,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.36321710973632,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 20.175438596491226,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.2093023255814,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.2840909090909,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.59791147564627,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.175675675675674,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.57092577427794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 22.988505747126435,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.830508474576272,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.137339805035392,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 11.11111111111111,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 28.994082840236686,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.151515151515152,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.86225330670592,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.5306479859895,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.36219336219335,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.04652387363518,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 104.91803278688525,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.98601398601399,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.85509325681492,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.832450687435184,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 39.27125506072874,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.82024432809774,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.97701149425288,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.58739558567517,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 105.26315789473684,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.962818003913892,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.760252365930597,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.123063597536447,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 27.27272727272727,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.029827315541601,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.615867404471228,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.215859030837004,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 185.51859099804304,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 151.88087774294672,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 186.57133091583208,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 164.31718061674007,
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
          "id": "83f3df2cbd814b277418b0e3aedd9cb706a14c35",
          "message": "Drop java home and fallback of python interpreter to interpreter_path (#243)\n\n* Remove depreacted JavaToochainInfo.{,boot_classpath_}java_home\n\n... now that all usages are removed.\n\n* pyton: drop fallback of interpreter to interpreter_path\n\n... as this happens already in the plugin.\n\n* Drop deprecated and no longer used artifact_location.from_execpath_do_not_use",
          "timestamp": "2026-09-28T16:15:36+02:00",
          "tree_id": "e8df10840f57e5b24a1b1435ed00e1a086ecddd1",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/83f3df2cbd814b277418b0e3aedd9cb706a14c35"
        },
        "date": 1790606380227,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 74.14772727272727,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.09049254692083,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 94.54545454545455,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.51282051282051,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.86407661447481,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 38.46153846153847,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.2093023255814,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.78753541076487,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.19321286167069,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 93.45794392523365,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.175675675675674,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.554183366707456,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 14.942528735632186,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.201183431952662,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.16949152542373,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.160055012582223,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 8.045977011494253,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.54054054054054,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.82076213375852,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.993006993007,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.5179856115108,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.98252361584505,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 89.06882591093117,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 36.96027633851468,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 31.818181818181817,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 37.191672656218586,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 32.677165354330704,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 112.60945709281962,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 97.12230215827337,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 113.04906267541719,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 104.0485829959514,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 29.215686274509807,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.044025157232703,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.438716683338157,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25.11013215859031,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.744075829383887,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.736762290448214,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 8.928571428571429,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 184.7358121330724,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 151.41509433962264,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.34834187868924,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 156.69642857142858,
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
          "id": "5ebe3b5b8bcc016c03184b84f9aaea9402cc75a7",
          "message": "Drop deprecated and no longer used Aspects enum (#244)\n\nFixes #187",
          "timestamp": "2026-09-28T17:49:01+02:00",
          "tree_id": "6c5fe023df3c2b149698126905b4e88aa1de941d",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/5ebe3b5b8bcc016c03184b84f9aaea9402cc75a7"
        },
        "date": 1790611716777,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 72.80453257790369,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.33771754110928,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 99.03846153846155,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.81395348837209,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.396600566572236,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.32829638811339,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 27.27272727272727,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.69158878504673,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.42372881355932,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.98151272767072,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 90.35087719298247,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 16.89189189189189,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.634582793029757,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 11.904761904761903,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.117647058823529,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.797297297297296,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.818168328803495,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 4.444444444444445,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.54054054054054,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.69978375788814,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 23.809523809523807,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.55555555555556,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.41630901287554,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.01566601802308,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 90.4,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 37.177280550774526,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 31.818181818181817,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 37.00241545182602,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 31.496062992125985,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 112.10526315789473,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 97.11399711399712,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.52724133551682,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 101.6,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.962818003913892,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.075709779179807,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.27549247742465,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 27.27272727272727,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.090019569471623,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.249211356466878,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.275323111163127,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 8.695652173913043,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 184.54011741682973,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 151.57232704402517,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.47184060630423,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 160.8695652173913,
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
          "id": "681a6a78dad22cd6ce77890d72a1f9b9c5406b6c",
          "message": "Bump buildifier_prebuilt from 10.0.1 to 10.1.0 (#245)\n\nBumps [buildifier_prebuilt](https://github.com/keith/buildifier-prebuilt) from 10.0.1 to 10.1.0.\n- [Release notes](https://github.com/keith/buildifier-prebuilt/releases)\n- [Commits](https://github.com/keith/buildifier-prebuilt/compare/10.0.1...10.1.0)\n\n---\nupdated-dependencies:\n- dependency-name: buildifier_prebuilt\n  dependency-version: 10.1.0\n  dependency-type: direct:production\n  update-type: version-update:semver-minor\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-29T14:14:36+02:00",
          "tree_id": "51c071020531322cffa6e4c22a00c32c7700bbcd",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/681a6a78dad22cd6ce77890d72a1f9b9c5406b6c"
        },
        "date": 1790685131114,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.41860465116278,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.57954545454545,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.28098330111109,
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
            "value": 20.170454545454543,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.168275077285934,
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
            "value": 74.22096317280453,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.47775414538457,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 87.27272727272727,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.52941176470588,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.162162162162163,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.68821339116545,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 26.436781609195403,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 12.941176470588237,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.508474576271185,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.228293818977262,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 15.476190476190476,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 26.47058823529412,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 14.965986394557824,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.371922288561542,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 3.1914893617021276,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.98080279232111,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.39598278335724,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.34044816247092,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.7434554973822,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.04597701149425,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.79463470633137,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 112.28070175438596,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.54178674351584,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.78727824318105,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.7109375,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 26.373626373626376,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 29.935020871290458,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.677103718199607,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.954186413902052,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.650844648606952,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.215859030837004,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 186.8884540117417,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 154.1139240506329,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.9119959898738,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 164.31718061674007,
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
          "id": "a62607ac7eb53ba636d6e817117120501c84aced",
          "message": "Bump rules_python from 2.3.3 to 2.3.4 (#246)\n\nBumps [rules_python](https://github.com/bazel-contrib/rules_python) from 2.3.3 to 2.3.4.\n- [Release notes](https://github.com/bazel-contrib/rules_python/releases)\n- [Changelog](https://github.com/bazel-contrib/rules_python/blob/main/CHANGELOG.md)\n- [Commits](https://github.com/bazel-contrib/rules_python/compare/2.3.3...2.3.4)\n\n---\nupdated-dependencies:\n- dependency-name: rules_python\n  dependency-version: 2.3.4\n  dependency-type: direct:production\n  update-type: version-update:semver-patch\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-09-29T17:43:57+02:00",
          "tree_id": "25593d163c1bffd9a8f7c99ebcbb58c4185a6ef8",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/a62607ac7eb53ba636d6e817117120501c84aced"
        },
        "date": 1790697902582,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.95348837209302,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.44632768361582,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.29536408730611,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.170454545454543,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.168275077285934,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 31.73076923076923,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.69158878504673,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.36619718309859,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.53383893015493,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 14.334470989761092,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.577164766828023,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 19.54022988505747,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 12.941176470588237,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.508474576271185,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.228293818977262,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 15.476190476190476,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.647058823529413,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.151515151515152,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.52592363667225,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 11.11111111111111,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.71929824561404,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.25251076040172,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.13221290166814,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 92.21789883268482,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.7434554973822,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.04597701149425,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.79463470633137,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.36363636363636,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.98278335724534,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.97558355441133,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 105.26315789473684,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.57142857142857,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.472527472527474,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.428428386570783,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 26.785714285714285,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.677103718199607,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.954186413902052,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.650844648606952,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.215859030837004,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 186.49706457925637,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 152.19435736677116,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.9686282139835,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 175.9090909090909,
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
          "id": "e86f5f8f82a1b17977250c5dc3546c392ad7173e",
          "message": "Propagate Xcode info over multiple edges  (#247)\n\nThe previous assumption was that the Xcode information is a direct dependency of the cc toolchain, however this is not the case when using the generated toolchain by the apple_support rules.",
          "timestamp": "2026-09-29T18:18:50+02:00",
          "tree_id": "d84335033318deafffc5b815f64aef54ec518606",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/e86f5f8f82a1b17977250c5dc3546c392ad7173e"
        },
        "date": 1790699724475,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.41860465116278,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.37110481586402,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.2043086746418,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 90.65420560747664,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 34.883720930232556,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 23.863636363636363,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.81541873485598,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 19.65811965811966,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.2093023255814,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 74.92877492877493,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.54368577396296,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 87.71929824561403,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.48028428274919,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 8.045977011494253,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.201183431952662,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.204081632653061,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.451474925982158,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 15.555555555555555,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.647058823529413,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 14.527027027027026,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.7757679014075,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 11.11111111111111,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.90861159929702,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.64161849710982,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.45019153214153,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 97.6,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 37.913043478260875,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.664756446991404,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.03634146630076,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.53846153846155,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.54676258992806,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.76021713640185,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 96.10894941634241,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.962818003913892,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.689873417721518,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.34313514367585,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 22.026431718061673,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.558869701726843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.4538537631683,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 14.732142857142858,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 186.66666666666666,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 151.81102362204723,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.84528386706853,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 162.9955947136564,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "274481315+jetbrains-air[bot]@users.noreply.github.com",
            "name": "jetbrains-air[bot]",
            "username": "jetbrains-air[bot]"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "89a99763124fcb549607def1b47c67e9bb71d8db",
          "message": "Bump maven dependencies (#248)\n\n- com.google.guava:guava: 33.7.1-jre -> 33.7.2-jre\n\nProduced by Air Automations. Name: Bump Maven Dependencies / Run: https://air.jetbrains.cloud/org/05cf1a7f-6ab5-713b-abd3-29d0c8a05e2d/automations/b06351af-1cef-4e3f-97a9-b63d18344d99?run=06803792-16dd-4483-ae77-347e78c96a96\n\nCo-authored-by: Air <noreply@air.dev>\nCo-authored-by: Claude Opus 5.5 (1M context) <noreply@anthropic.com>",
          "timestamp": "2026-09-30T11:57:46+02:00",
          "tree_id": "863fc9680513406b724ada15249f326597b29d7d",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/89a99763124fcb549607def1b47c67e9bb71d8db"
        },
        "date": 1790763413772,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.01869158878503,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 72.67605633802818,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.0475288399124,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 93.45794392523365,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 34.883720930232556,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 23.863636363636363,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.81541873485598,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 19.65811965811966,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 116.74418604651163,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.85227272727273,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.15469149900632,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 101.92307692307692,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.48028428274919,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 8.045977011494253,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.764309764309765,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.191975755854585,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 11.494252873563218,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.647058823529413,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.54054054054054,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.80856768206329,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 26.436781609195403,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.26086956521739,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.53945480631278,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.87686481016527,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 97.6,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 37.913043478260875,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.664756446991404,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.03634146630076,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.34380453752182,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 95.70200573065902,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.57527241827869,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 102.8,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.767123287671232,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.27272727272727,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.4656480059154,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 24.782608695652176,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.558869701726843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.4538537631683,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 14.732142857142858,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 186.8884540117417,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 152.03761755485894,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 186.2315488542394,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 161.67400881057267,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "justin.kaeser@jetbrains.com",
            "name": "Justin Kaeser",
            "username": "jastice"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "4159cda1f4002231787b3ad21ac0dedd8f7b03c0",
          "message": "Read target metadata without Reader buffers (#249)\n\nPass decoded UTF-8 text directly to TextFormat. Removes is the growing StringBuilder and adds a byte[] copy of the file, so the memory used at peak is about the same. A speedup is plausible.",
          "timestamp": "2026-10-01T20:34:41+02:00",
          "tree_id": "ff4262d4e4c8070addabaebe42c74f58fbd33f49",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/4159cda1f4002231787b3ad21ac0dedd8f7b03c0"
        },
        "date": 1790880888411,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 74.14772727272727,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.38410203842054,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 113.99999999999999,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 34.883720930232556,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 23.863636363636363,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.81541873485598,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 19.65811965811966,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.2840909090909,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.30119750796742,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.48028428274919,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 8.045977011494253,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 12.941176470588237,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.45945945945946,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 13.563263863173558,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 17.5,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.647058823529413,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.151515151515152,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.2710155021989,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 26.666666666666668,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.16783216783216,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.52873563218391,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.67123698336307,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 96.39999999999999,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 37.913043478260875,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.664756446991404,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.03634146630076,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 112.63157894736841,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 97.54689754689755,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.99000515508912,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 100,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 29.411764705882355,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 28.322784810126585,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.473707605687615,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 18.94273127753304,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.558869701726843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.4538537631683,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 14.732142857142858,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 185.09803921568627,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 153.1645569620253,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.65283780425327,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 160.26785714285714,
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
          "id": "7219c45893cd8cc723776dcbb7d3c0870486a55b",
          "message": "Bump aspect_rules_lint from 2.9.0 to 2.9.1 (#252)\n\nBumps [aspect_rules_lint](https://github.com/aspect-build/rules_lint) from 2.9.0 to 2.9.1.\n- [Release notes](https://github.com/aspect-build/rules_lint/releases)\n- [Commits](https://github.com/aspect-build/rules_lint/compare/v2.9.0...v2.9.1)\n\n---\nupdated-dependencies:\n- dependency-name: aspect_rules_lint\n  dependency-version: 2.9.1\n  dependency-type: direct:production\n  update-type: version-update:semver-patch\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-10-06T10:01:26+02:00",
          "tree_id": "071f9ebf3d04852c2883b7e1cbeff3995793f488",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/7219c45893cd8cc723776dcbb7d3c0870486a55b"
        },
        "date": 1791274922800,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.42592592592592,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.65439093484419,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.42293898969038,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 96.26168224299066,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 34.883720930232556,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 23.863636363636363,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.81541873485598,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 19.65811965811966,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.22429906542055,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.63739376770539,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.77263889881868,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 105.76923076923077,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.48028428274919,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 8.045977011494253,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.201183431952662,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.921501706484642,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.423426249108386,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 3.4482758620689653,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 14.18918918918919,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.33578883542845,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 23.809523809523807,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 106.26086956521739,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.82639885222382,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.25813815552851,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 98.8,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 37.913043478260875,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.664756446991404,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.03634146630076,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.34380453752182,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.97841726618705,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.90184480358197,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 101.6,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 29.158512720156555,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.488151658767773,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.416691792894255,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 29.09090909090909,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.558869701726843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.4538537631683,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 14.732142857142858,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 187.45098039215685,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 154.43037974683546,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 186.82729706986657,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 156.25,
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
          "id": "0fa5d8372c58a4c3a2fc4079f493987791e29ca9",
          "message": "Bump rules_go from 0.63.0 to 0.64.1 (#253)\n\nBumps [rules_go](https://github.com/bazel-contrib/rules_go) from 0.63.0 to 0.64.1.\n- [Release notes](https://github.com/bazel-contrib/rules_go/releases)\n- [Commits](https://github.com/bazel-contrib/rules_go/compare/v0.63.0...v0.64.1)\n\n---\nupdated-dependencies:\n- dependency-name: rules_go\n  dependency-version: 0.64.1\n  dependency-type: direct:production\n  update-type: version-update:semver-minor\n...\n\nSigned-off-by: dependabot[bot] <support@github.com>\nCo-authored-by: dependabot[bot] <49699333+dependabot[bot]@users.noreply.github.com>",
          "timestamp": "2026-10-06T10:01:52+02:00",
          "tree_id": "c9202d0802fdf7ca2ade8f9bbbf491a08af7e21c",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/0fa5d8372c58a4c3a2fc4079f493987791e29ca9"
        },
        "date": 1791276040233,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.41860465116278,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 70.17045454545455,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.45437241066345,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 89,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 34.883720930232556,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 23.863636363636363,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.81541873485598,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 19.65811965811966,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 116.27906976744187,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.85227272727273,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.95524894343714,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 102.803738317757,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.48028428274919,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 8.045977011494253,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.201183431952662,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.135135135135135,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.490682142470149,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 10.638297872340425,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.306122448979592,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 29.062661096690668,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 22.22222222222222,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.71929824561404,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.36219336219335,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.32118428861708,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 98.8,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 37.913043478260875,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.664756446991404,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.03634146630076,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36.43724696356275,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.95652173913044,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 93.96551724137932,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.46551384986402,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 84.04669260700389,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 29.411764705882355,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.1585557299843,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.36186674236097,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 26.785714285714285,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 12.558869701726843,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.4538537631683,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 14.732142857142858,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 184.765625,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 153.47003154574134,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.32254298472623,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 164.31718061674007,
            "unit": "%"
          }
        ]
      },
      {
        "commit": {
          "author": {
            "email": "justin.kaeser@jetbrains.com",
            "name": "Justin Kaeser",
            "username": "jastice"
          },
          "committer": {
            "email": "noreply@github.com",
            "name": "GitHub",
            "username": "web-flow"
          },
          "distinct": true,
          "id": "a1f4bd7a768d758de2cb59746a293193dd7e74b9",
          "message": "Omit the default compile dependency type (#251)\n\nCOMPILE_TIME is the zero-valued enum default in the proto3 schema. Thus,\nin this case that field can be omitted without loosing any data.",
          "timestamp": "2026-10-06T10:42:51+02:00",
          "tree_id": "4e32cf5956a8f327ba75102e1c84f2dd2011aefa",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/a1f4bd7a768d758de2cb59746a293193dd7e74b9"
        },
        "date": 1791277398301,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.95348837209302,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.65439093484419,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.21441495177137,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 90.9090909090909,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.981308411214954,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 19.8300283286119,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.516813258707316,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 20.175438596491226,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.13953488372093,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 73.87640449438202,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.66465879075973,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 94.54545454545455,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.35294117647059,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.794612794612794,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.16540550793515,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 10.638297872340425,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.135135135135135,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.193837678861232,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 19.047619047619047,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.810650887573964,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.202702702702704,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.501177750582606,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 26.436781609195403,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 108.43585237258348,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 93.79509379509379,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 108.79242079811394,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 101.21457489878543,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 39.05429071803853,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.477633477633475,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 39.02450312213976,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 38.114754098360656,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.14982578397212,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.12068965517241,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.46213902276855,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 86.8,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.962818003913892,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.67295597484277,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.356332715647987,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 28.125,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.50980392156863,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.924050632911392,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.741854086594733,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 10.434782608695652,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 185.32289628180038,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 152.43328100470958,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 186.28457111326037,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 174.54545454545453,
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
          "id": "fdda5f228eb97bf68dda284dfd8d6b8f3da067ea",
          "message": "Update testing range for rules_go (#254)",
          "timestamp": "2026-10-06T12:08:05+02:00",
          "tree_id": "be4b9cce137ea838e7ae7f5e3d267808e79e3301",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/fdda5f228eb97bf68dda284dfd8d6b8f3da067ea"
        },
        "date": 1791282193204,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 113.48837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 72.03389830508475,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 113.56930539545931,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 86.91588785046729,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 19.491525423728813,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.50517634146048,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 41.23711340206185,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 117.67441860465115,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 72.93447293447294,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 116.98883742977893,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 101.03092783505154,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 22.941176470588236,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 12.54237288135593,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.832941303895165,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 15.555555555555555,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 9.491525423728813,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 13.977586991648,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 14.942528735632186,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.976190476190478,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 16.271186440677965,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 29.68508762541501,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 18.88888888888889,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.7391304347826,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.69054441260745,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.08732506347609,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 103.68852459016394,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.19444444444444,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.857142857142854,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.193509075570745,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 110.99476439790577,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.12068965517241,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.5450576097664,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 102.8,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.767123287671232,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 26.959247648902824,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.153354263065683,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 25.11013215859031,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.285714285714285,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.428120063191153,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.414322608270588,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 11.894273127753303,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 187.2549019607843,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 153.2385466034755,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 186.0748033915087,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 174.54545454545453,
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
          "id": "9887b040c01ab1522442895494b5fec87c9653c7",
          "message": "Add support for foreign_cc (#255)\n\n* Add support for rules_foreign_cc\n\nAnother way to define CC libraries is rules_foreign_cc. All the\ninformation needed to build against them is already contained in\nCcInfo, so new information is needed. However, we need to ensure\nthat if the build output group is requested, the headers (which are\ngenerated by design) actually get built. By the way rules_foreign_cc\nis organised internally, this does not happen by the cc_info aspect\nrequesting all non-source headers. So we have trigger the (only,\ngigantic) action of a rules_foreign_cc target by requesting output\nthrough a specialized module. In order to unambigiously signal that\na target was recognized as foreign, we report the rule kind in the\nForeignCcInfo message.\n\n* Add test for foreign make\n\n... using, essentially, the example given in rules_foreign_cc, however\nwith missing dependencies and destdir support added to the Makefile.\n\n* Temporarily bump python requirement for BCR deployment\n\n... to avoid problems with rules_python 1.9.0 on Windows CI.",
          "timestamp": "2026-10-07T20:39:47+02:00",
          "tree_id": "224718853f725fee2ead6ced7c4ae93806c8477b",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/9887b040c01ab1522442895494b5fec87c9653c7"
        },
        "date": 1791399577331,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 115.42056074766356,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.08781869688386,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.34495997953564,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 113.99999999999999,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 35.348837209302324,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.113314447592067,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.3572107102925,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 44.329896907216494,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 118.22429906542055,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 75.85227272727273,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 117.27989862866237,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 99.03846153846155,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.60544217687075,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.279344798258194,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 6.382978723404255,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 14.117647058823529,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.135135135135135,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.41200674173826,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 7.777777777777778,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.058823529411764,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.254237288135593,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.964954917269004,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 11.11111111111111,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 105.72916666666667,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 91.84549356223177,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 106.20694699025084,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 97.6,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 39.54305799648506,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 33.477633477633475,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 39.5300198961009,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 34.8,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.69284467713787,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.69064748201438,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 112.24422784851065,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 86.4,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 29.411764705882355,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.629513343799054,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.560028681149504,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 28.125,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.481409001956946,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.744075829383887,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.628236774481381,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.392857142857142,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 186.8884540117417,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 152.43328100470958,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 185.9807852814635,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 162.9955947136564,
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
          "id": "0d4f4c49c4b88c059b92dce5761db9a2911b67e9",
          "message": "Increase testing range for rules_pyton (#256)\n\n... and also add a relevant inbetween version.",
          "timestamp": "2026-10-08T09:06:21+02:00",
          "tree_id": "d7c9b4647af12e9dda8998fd90c55912cb7bd179",
          "url": "https://github.com/JetBrains/intellij-aspect/commit/0d4f4c49c4b88c059b92dce5761db9a2911b67e9"
        },
        "date": 1791444478850,
        "tool": "customSmallerIsBetter",
        "benches": [
          {
            "name": "bazel-nosync:used-heap-size-after-gc",
            "value": 114.95327102803739,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:peak-heap-size",
            "value": 73.93767705382436,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_used",
            "value": 114.63820065399321,
            "unit": "%"
          },
          {
            "name": "bazel-nosync:analysis_heap_committed",
            "value": 76.92307692307693,
            "unit": "%"
          },
          {
            "name": "bazel-plain:used-heap-size-after-gc",
            "value": 36.44859813084112,
            "unit": "%"
          },
          {
            "name": "bazel-plain:peak-heap-size",
            "value": 20.113314447592067,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_used",
            "value": 34.44867080910628,
            "unit": "%"
          },
          {
            "name": "bazel-plain:analysis_heap_committed",
            "value": 27.27272727272727,
            "unit": "%"
          },
          {
            "name": "bazel:used-heap-size-after-gc",
            "value": 119.1588785046729,
            "unit": "%"
          },
          {
            "name": "bazel:peak-heap-size",
            "value": 76.92307692307693,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_used",
            "value": 118.17259482408542,
            "unit": "%"
          },
          {
            "name": "bazel:analysis_heap_committed",
            "value": 81.57894736842105,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:used-heap-size-after-gc",
            "value": 23.668639053254438,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:peak-heap-size",
            "value": 13.220338983050848,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_used",
            "value": 24.57821449125169,
            "unit": "%"
          },
          {
            "name": "bbremote-nosync:analysis_heap_committed",
            "value": 14.942528735632186,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:used-heap-size-after-gc",
            "value": 13.529411764705882,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:peak-heap-size",
            "value": 10.135135135135135,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_used",
            "value": 14.66944449492705,
            "unit": "%"
          },
          {
            "name": "bbremote-plain:analysis_heap_committed",
            "value": 19.54022988505747,
            "unit": "%"
          },
          {
            "name": "bbremote:used-heap-size-after-gc",
            "value": 27.810650887573964,
            "unit": "%"
          },
          {
            "name": "bbremote:peak-heap-size",
            "value": 15.932203389830507,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_used",
            "value": 28.697828495720394,
            "unit": "%"
          },
          {
            "name": "bbremote:analysis_heap_committed",
            "value": 30.952380952380953,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:used-heap-size-after-gc",
            "value": 107.18038528896672,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:peak-heap-size",
            "value": 92.5179856115108,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_used",
            "value": 107.59919762820012,
            "unit": "%"
          },
          {
            "name": "intellij-nosync:analysis_heap_committed",
            "value": 97.6,
            "unit": "%"
          },
          {
            "name": "intellij-plain:used-heap-size-after-gc",
            "value": 38.26086956521739,
            "unit": "%"
          },
          {
            "name": "intellij-plain:peak-heap-size",
            "value": 32.99856527977044,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_used",
            "value": 38.25812050574069,
            "unit": "%"
          },
          {
            "name": "intellij-plain:analysis_heap_committed",
            "value": 36,
            "unit": "%"
          },
          {
            "name": "intellij:used-heap-size-after-gc",
            "value": 111.34380453752182,
            "unit": "%"
          },
          {
            "name": "intellij:peak-heap-size",
            "value": 96.12068965517241,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_used",
            "value": 111.67461791129244,
            "unit": "%"
          },
          {
            "name": "intellij:analysis_heap_committed",
            "value": 91.43968871595331,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:used-heap-size-after-gc",
            "value": 28.767123287671232,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:peak-heap-size",
            "value": 27.129337539432175,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_used",
            "value": 30.029990850736443,
            "unit": "%"
          },
          {
            "name": "pigweed-nosync:analysis_heap_committed",
            "value": 26.431718061674008,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:used-heap-size-after-gc",
            "value": 14.0625,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:peak-heap-size",
            "value": 13.58609794628752,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_used",
            "value": 14.432234124375153,
            "unit": "%"
          },
          {
            "name": "pigweed-plain:analysis_heap_committed",
            "value": 13.392857142857142,
            "unit": "%"
          },
          {
            "name": "pigweed:used-heap-size-after-gc",
            "value": 187.47553816046968,
            "unit": "%"
          },
          {
            "name": "pigweed:peak-heap-size",
            "value": 152.74725274725273,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_used",
            "value": 186.4683938531219,
            "unit": "%"
          },
          {
            "name": "pigweed:analysis_heap_committed",
            "value": 139.1304347826087,
            "unit": "%"
          }
        ]
      }
    ]
  }
}