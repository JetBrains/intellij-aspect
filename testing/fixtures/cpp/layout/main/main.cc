#include "lib/generated.h"
#include "src/lib.h"
#include "textual/header.h"
#include "hdronly/hdr.h"

int main() {
  return greeting() + lib_value() + textual_value() + hdronly_value();
}
