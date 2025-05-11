import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const API = process.env.EXPO_PUBLIC_API_BASE_URL;

async function getAuthHeader() {
  const token = await AsyncStorage.getItem('accessToken');
  return {
    Authorization: `Bearer ${token}`,
  };
}

export async function fetchStudyRooms() {
  const headers = await getAuthHeader();
  const res = await axios.get(`${API}/api/study-rooms`, { headers });
  return res.data.data;
}

export const fetchFilteredStudyRooms = async (region?: string, payType?: string) => {
    const headers = await getAuthHeader();
    const params: Record<string, string> = {};
    if (region) params.region = region;
    if (payType) params.payType = payType;
  
    const res = await axios.get(`${API}/api/study-rooms/filter`, { params, headers });
    return res.data.data;
  };
  
  export const fetchStudyRoomRegions = async () => {
    const headers = await getAuthHeader();
    const res = await axios.get(`${API}/api/study-rooms/regions`, { headers });
    return res.data.data;
  };
  
