export type StudyRoom = {
    id: number;
    name: string;
    region: string;
    payType: string;
    category: string;
    imageUrl: string;
  };
  
  export type StudyRoomDetail = StudyRoom & {
    intro: string;
    openingHours: string;
    address: string;
    contact: string;
  };
  