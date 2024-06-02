class Carriage:
    def __init__(self, car_type, amount, to_download):
        self.car_type = car_type
        self.amount = amount
        self.to_download = to_download

    def __str__(self):
        return f"{self.car_type}({self.amount})"


class PassengerCar(Carriage):
    def __init__(self, car_type, amount, to_download):
        Carriage.__init__(self, car_type, amount, to_download)
        self.passengers = amount


class GoodsCar(Carriage):
    def __init__(self, car_type, amount, to_download):
        Carriage.__init__(self, car_type, amount, to_download)
        self.goods = amount


class Train:
    def __init__(self, name, arrival_time, departure_time, train_type, carriages):
        self.name = name
        self.arrival_time = arrival_time
        self.departure_time = departure_time
        self.train_type = train_type
        self.engine = "E"
        self.carriages = carriages

    def __str__(self):
        carriage_str = ""
        for car in self.carriages:
            carriage_str += str(car) + " "
        return f"{self.name} : {self.arrival_time} : ARRIVED : {self.engine} {carriage_str.strip()}"

    def get_download_info(self):
        total_to_download = 0
        for car in self.carriages:
            total_to_download += car.to_download
        if self.train_type == "P":
            return f"{total_to_download} passengers were downloaded."
        else:
            return f"{total_to_download} goods were downloaded."

    def reverse_order(self):
        carriage_str = ""
        for i in range(len(self.carriages) - 1, -1, -1):
            carriage_str += str(self.carriages[i]) + " "
        return f"{self.name} : LEFT : {carriage_str.strip()} {self.engine}"


class PassengerTrain(Train):
    def __init__(self, name, arrival_time, departure_time, carriages):
        Train.__init__(self, name, arrival_time, departure_time, "P", carriages)
        self.total_passengers = 0
        self.passengers_to_download = 0
        for car in carriages:
            self.total_passengers += car.passengers
            self.passengers_to_download += car.to_download


class GoodsTrain(Train):
    def __init__(self, name, arrival_time, departure_time, carriages):
        Train.__init__(self, name, arrival_time, departure_time, "G", carriages)
        self.total_goods = 0
        self.goods_to_download = 0
        for car in carriages:
            self.total_goods += car.goods
            self.goods_to_download += car.to_download


class PassengerTrack:
    def __init__(self):
        self.status = "EMPTY"
        self.last_departure_time = None
        self.track_type = "PASSENGER"

    def is_occupied(self):
        return self.status == "OCCUPIED"

    def occupy(self):
        self.status = "OCCUPIED"

    def release(self, current_time):
        self.status = "EMPTY"
        self.last_departure_time = current_time


class GoodsTrack:
    def __init__(self):
        self.status = "EMPTY"
        self.last_departure_time = None
        self.track_type = "GOODS"

    def is_occupied(self):
        return self.status == "OCCUPIED"

    def occupy(self):
        self.status = "OCCUPIED"

    def release(self, current_time):
        self.status = "EMPTY"
        self.last_departure_time = current_time


class Station:
    def __init__(self):
        self.passenger_track = PassengerTrack()
        self.goods_track = GoodsTrack()
        self.trains = []
        self.events = []
        self.serviced_trains = 0
        self.passenger_trains_serviced = 0
        self.goods_trains_serviced = 0
        self.total_passengers_downloaded = 0
        self.total_goods_downloaded = 0
        self.intermediate_passengers = 0
        self.future_goods = 0
        self.refused_trains = 0
        self.refused_passenger_trains = 0
        self.refused_goods_trains = 0
        self.potential_passengers_loss = 0
        self.potential_goods_loss = 0

    def take_input(self):
        print("Enter train information in the following format:")
        print(
            "T <arrival_time> <departure_time> <train_name> <train_type> <engine> <carriages>"
        )
        print("Press enter to finish and see the report.")
        while True:
            line = input().strip()
            if not line:
                break
            self.process_train(line)

    def process_train(self, line):
        parts = line.split()
        if len(parts) < 7 or parts[0] != "T":
            print("Invalid input format. Please enter train information again.")
            return
        arrival_time = parts[1]
        departure_time = parts[2]
        name = parts[3]
        train_type = parts[4]
        engine = parts[5]
        carriages = self.parse_carriages(parts[6:], train_type)
        if carriages is None:
            print("Invalid carriage information. Please enter train information again.")
            return
        if train_type == "P":
            train = PassengerTrain(name, arrival_time, departure_time, carriages)
        elif train_type == "G":
            train = GoodsTrain(name, arrival_time, departure_time, carriages)
        else:
            print("Invalid train type. Please enter train information again.")
            return
        self.trains.append(train)

    def parse_carriages(self, parts, train_type):
        carriages = []
        try:
            for i in range(0, len(parts), 3):
                car_type = parts[i]
                amount = int(parts[i + 1])
                to_download = int(parts[i + 2])
                if train_type == "P" and car_type in "ABC":
                    carriages.append(PassengerCar(car_type, amount, to_download))
                elif train_type == "G" and car_type in "WB":
                    carriages.append(GoodsCar(car_type, amount, to_download))
                else:
                    return None
        except (IndexError, ValueError):
            return None
        return carriages

    def simulate(self):
        for train in self.trains:
            self.handle_arrival(train)
            self.handle_departure(train, train.departure_time)
        self.generate_report()

    def handle_arrival(self, train):
        if train.train_type == "P" and not self.passenger_track.is_occupied():
            self.passenger_track.occupy()
            self.events.append((train.arrival_time, f"{train}"))
            self.events.append(
                (
                    train.arrival_time,
                    f"{train.name} : {train.arrival_time} : {train.get_download_info()}",
                )
            )
            self.events.append(
                (
                    train.arrival_time,
                    f"PASSENGER TRACK : {train.arrival_time} : OCCUPIED",
                )
            )
            self.passenger_trains_serviced += 1
            self.total_passengers_downloaded += train.passengers_to_download
            self.intermediate_passengers += (
                train.total_passengers - train.passengers_to_download
            )
        elif train.train_type == "G" and not self.goods_track.is_occupied():
            self.goods_track.occupy()
            self.events.append((train.arrival_time, f"{train}"))
            self.events.append(
                (
                    train.arrival_time,
                    f"{train.name} : {train.arrival_time} : {train.get_download_info()}",
                )
            )
            self.events.append(
                (train.arrival_time, f"GOODS TRACK : {train.arrival_time} : OCCUPIED")
            )
            self.goods_trains_serviced += 1
            self.total_goods_downloaded += train.goods_to_download
            self.future_goods += train.total_goods - train.goods_to_download
        else:
            self.events.append(
                (
                    train.arrival_time,
                    f"{train.name} : {train.arrival_time} : refused to enter",
                )
            )
            self.refused_trains += 1
            if train.train_type == "P":
                self.refused_passenger_trains += 1
                self.potential_passengers_loss += sum(
                    5 if car.car_type == "A" else 10 if car.car_type == "B" else 50
                    for car in train.carriages
                )
            elif train.train_type == "G":
                self.refused_goods_trains += 1
                self.potential_goods_loss += sum(10 for car in train.carriages)

    def handle_departure(self, train, current_time):
        if train.train_type == "P":
            track = self.passenger_track
        else:
            track = self.goods_track

        if (
            track.is_occupied()
            and track.last_departure_time
            and (int(current_time) - int(track.last_departure_time)) < 5
        ):
            self.events.append(
                (current_time, f"{train.name} : {current_time} : refused to leave")
            )
        else:
            track.release(current_time)
            self.events.append((current_time, train.reverse_order()))
            self.events.append(
                (current_time, f"{track.track_type} TRACK : {current_time} : EMPTY")
            )
            self.serviced_trains += 1

    def generate_report(self):
        for event in sorted(self.events, key=lambda x: x[0]):
            print(event[1])

        print(f"{self.serviced_trains} trains were serviced.")
        print(f"{self.passenger_trains_serviced} passenger trains were serviced.")
        print(f"{self.goods_trains_serviced} goods trains were serviced.")
        print(f"{self.total_passengers_downloaded} passengers got off at this station.")
        print(
            f"{self.total_goods_downloaded} units of goods were downloaded at this station."
        )
        print(
            f"{self.intermediate_passengers} passengers used this station as an intermediate point."
        )
        print(
            f"{self.future_goods} units of goods passed through this station for a future destination."
        )
        print(
            f"{self.refused_trains} trains were refused entry because of scheduling conflicts."
        )
        print(
            f"Of these, there were {self.refused_passenger_trains} passenger trains and {self.refused_goods_trains} goods trains."
        )
        print(
            f"This means a potential loss of {self.potential_passengers_loss} people not coming into town, and {self.potential_goods_loss} units of goods not coming into town."
        )


if __name__ == "__main__":
    station = Station()
    station.take_input()
    station.simulate()
