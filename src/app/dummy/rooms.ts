export interface IFloor {
  id: number;
  name: string;
}

export class Floor {
  id: number;
  name: string;
  constructor(floor: IFloor) {
    this.id = floor.id;
    this.name = floor.name;
  }
}

export interface IRoom {
  id: any;
  name: string;
  floor: IFloor;
}

export class Room {
    id: number;
    name: string;
    floor: Floor;

    constructor(room : IRoom) {
      this.id = room.id;
      this.name = room.name;
      this.floor = room.floor;
    }
  }

export interface IBed {
  id: any;
  name: string;
  room: IRoom;
}

export const ROOMS: any[] = [
  { id: 0, text: "MEDECINE INTERNE ET GENERALE" },
  { id: 1, text: "PEDIATRIE" },
  { id: 2, text: "CARDIOLOGIE" },
  { id: 3, text: "NEUROLOGIE" },
];
