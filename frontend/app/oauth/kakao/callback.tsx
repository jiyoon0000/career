import { useLocalSearchParams, useRouter } from 'expo-router';
import { useEffect } from 'react';
import axios from 'axios';

export default function KakaoCallbackPage() {
  const router = useRouter();
  const { code } = useLocalSearchParams();

  useEffect(() => {
    if (code && typeof code === 'string') {
      axios
        .get(`https://api.careeroom.net/api/auth/kakao/callback?code=${code}`)
        .then((res) => {
          const { accessToken, refreshToken } = res.data;
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('refreshToken', refreshToken);
          router.replace('/tabs');
        })
        .catch((err) => {
          console.error('카카오 로그인 실패', err);
          router.replace('/login');
        });
    }
  }, [code]);

  return null;
}
