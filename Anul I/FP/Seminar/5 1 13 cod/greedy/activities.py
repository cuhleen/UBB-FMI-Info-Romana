class Activity:
    def __init__(self, s, f):
        self.__start_time = s
        self.__finish_time = f

    @property
    def start_time(self):
        return self.__start_time

    @property
    def finish_time(self):
        return self.__finish_time

    def __str__(self):
        return "Activity: " + str(self.__start_time) + "-> " + str(self.__finish_time)


def selectMostPromising(c):
    return c[0]


def acceptable(activity_list):
    if len(activity_list) == 1:
        return True
    last_activity = activity_list[-1]
    return last_activity.start_time >= activity_list[-2].finish_time


def greedy(c):
    b = []
    while len(c) != 0:
        candidate = selectMostPromising(c)
        c.remove(candidate)
        if acceptable(b + [candidate]):
            b.append(candidate)
    return b


#
# a1 = Activity(2, 6)
# a2 = Activity(5, 8)
# a3 = Activity(7, 12)
a1 = Activity(9, 11)
a7 = Activity(12, 13)
a2 = Activity(10, 12)
a3 = Activity(16, 18)
a4 = Activity(14, 16)
a5 = Activity(20, 22)
a6 = Activity(19, 21)
a8 = Activity(8, 10)

activity_list = [a1, a2, a3, a4, a5, a6, a7, a8]
sorted_list = sorted(activity_list, key=lambda activity: activity.finish_time)

sol = greedy(sorted_list)
for activity in sol:
    print(activity)
