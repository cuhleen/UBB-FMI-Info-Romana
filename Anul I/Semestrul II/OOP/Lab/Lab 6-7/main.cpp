#include "Tests.h"
#include "Interface.h"
int main() {
    const FilmRepo productRepo;
    constexpr Validation validator;
    const Service service(productRepo, validator);
    Interface ui(service);

    Tests::runAllTests();
    ui.Run();
    return 0;
}