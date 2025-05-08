#ifndef UI_H
#define UI_H
#include <utility>

#include "Service.h"

class Interface {
private:
    Service service;
public:
    explicit Interface(Service service) : service(std::move(service)) {}
    void Run();
};



#endif //UI_H
