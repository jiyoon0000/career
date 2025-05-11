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
