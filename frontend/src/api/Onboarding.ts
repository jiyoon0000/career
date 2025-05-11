import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const API = process.env.EXPO_PUBLIC_API_BASE_URL;

async function getAuthHeader() {
  const token = await AsyncStorage.getItem('accessToken');
  return {
    Authorization: `Bearer ${token}`,
  };
}

export async function searchJobs(keyword: string) {
  const headers = await getAuthHeader();
  const res = await axios.get(`${API}/api/onboarding/jobs/search`, {
    params: { keyword },
    headers,
  });
  return res.data.data;
}

export async function saveSelectedJob(jobCode: string) {
  const headers = await getAuthHeader();
  const res = await axios.post(
    `${API}/api/onboarding/jobs`,
    { jobCode },
    { headers }
  );
  return res.data.data;
}

export async function recommendSkills() {
  const headers = await getAuthHeader();
  const res = await axios.get(`${API}/api/onboarding/skills/recommend`, { headers });
  return res.data.data;
}

export async function saveSelectedSkills(skills: string[]) {
  const headers = await getAuthHeader();
  const res = await axios.post(
    `${API}/api/onboarding/skills`,
    { skills },
    { headers }
  );
  return res.data;
}

export async function recommendCertificates() {
  const headers = await getAuthHeader();
  const res = await axios.get(`${API}/api/onboarding/certificates/recommend`, { headers });
  return res.data.data;
}

export async function saveSelectedCertificates(certificateNames: string[]) {
  const headers = await getAuthHeader();
  const res = await axios.post(
    `${API}/api/onboarding/certificates`,
    { certificateNames },
    { headers }
  );
  return res.data;
}

export async function completeOnboarding() {
    const headers = await getAuthHeader();
    const res = await axios.post(`${API}/api/onboarding/complete`, {}, { headers });
    return res.data;
  }

export async function checkOnboardingCompleted() {
  const headers = await getAuthHeader();
  const res = await axios.get(`${API}/api/onboarding/completed`, { headers });
  return res.data.data.completed as boolean;
}
