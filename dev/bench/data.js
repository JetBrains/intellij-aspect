window.BENCHMARK_DATA = {
  "lastUpdate": 1789481146995,
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
      }
    ]
  }
}