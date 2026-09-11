window.BENCHMARK_DATA = {
  "lastUpdate": 1789141308230,
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
      }
    ]
  }
}