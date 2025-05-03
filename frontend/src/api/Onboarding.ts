import axios from 'axios';

export const searchJobs = async (keyword: string) => {
  try {
    const response = await axios.get(
      `${process.env.EXPO_PUBLIC_API_BASE_URL}/api/onboarding/jobs/search`,
      {
        params: { keyword },
      }
    );
    return response.data.data;
  } catch (error) {
    console.error('직무 검색 실패:', error);
    return [];
  }
};
