import React, { useEffect, useState, useRef } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  StyleSheet,
  Alert,
} from 'react-native';
import { router, useLocalSearchParams } from 'expo-router';
import { sendVerificationCode } from '@/api/Auth';

export default function VerificationCodeScreen() {
  const { email } = useLocalSearchParams<{ email: string }>();
  const [code, setCode] = useState('');
  const [timer, setTimer] = useState(300);
  const intervalRef = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    intervalRef.current = setInterval(() => {
      setTimer(prev => {
        if (prev <= 1) {
          clearInterval(intervalRef.current!);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => {
      clearInterval(intervalRef.current!);
    };
  }, []);

  const formatTime = (seconds: number) => {
    const m = Math.floor(seconds / 60)
      .toString()
      .padStart(2, '0');
    const s = (seconds % 60).toString().padStart(2, '0');
    return `${m}:${s}`;
  };

  const handleVerify = () => {
    if (code.length === 0) {
      Alert.alert('인증코드를 입력해 주세요.');
      return;
    }
    router.push({
      pathname: '/(auth)/join/password',
      params: { email },
    });
  };

  const handleResend = async () => {
    try {
      if (!email) return;
      await sendVerificationCode({ email });
      setTimer(300);
      Alert.alert('인증코드를 재전송했습니다.');
    } catch (error) {
      Alert.alert('오류', '인증코드 재전송에 실패했습니다.');
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => router.back()}>
          <Image
            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
            style={styles.backIcon}
          />
        </TouchableOpacity>
        <Text style={styles.title}>회원가입</Text>
      </View>

      <View style={styles.progressBar}>
        <View style={styles.progress} />
      </View>

      <View style={styles.content}>
        <Text style={styles.heading}>
          이메일로 발송된{'\n'}인증코드를 입력해 주세요
        </Text>
        <Text style={styles.sub}>
          {email ? `${email} 으로 보냈어요! 유효시간 ${formatTime(timer)}` : '이메일을 불러올 수 없습니다.'}
        </Text>

        <View style={styles.inputWrapper}>
          <TextInput
            placeholder="인증코드를 입력해 주세요"
            value={code}
            onChangeText={setCode}
            keyboardType="number-pad"
            style={styles.input}
          />
        </View>

        <View style={styles.resendRow}>
          <Text style={styles.resendText}>인증코드 메일이 오지 않았나요?</Text>
          <TouchableOpacity onPress={handleResend}>
            <Text style={styles.resendButton}>재전송</Text>
          </TouchableOpacity>
        </View>

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: code ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleVerify}
          disabled={!code}
        >
          <Text style={[styles.buttonText, { color: code ? '#fff' : '#999' }]}>
            인증코드 확인
          </Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#fff' },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: 44,
    paddingHorizontal: 20,
  },
  backIcon: { width: 36, height: 44 },
  title: { fontSize: 20, fontWeight: 'bold', color: '#111' },
  progressBar: {
    height: 6,
    backgroundColor: '#F1F1F5',
    borderRadius: 100,
    marginHorizontal: 20,
    marginTop: 8,
  },
  progress: {
    width: '66%',
    height: 6,
    backgroundColor: '#2379FA',
    borderRadius: 100,
  },
  content: {
    paddingHorizontal: 20,
    paddingTop: 32,
  },
  heading: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111',
    marginBottom: 8,
  },
  sub: {
    fontSize: 14,
    color: '#767676',
    marginBottom: 32,
  },
  inputWrapper: {
    borderWidth: 1,
    borderColor: '#E5E5EC',
    borderRadius: 12,
    paddingHorizontal: 16,
    paddingVertical: 4,
    marginBottom: 24,
  },
  input: {
    fontSize: 16,
    color: '#111',
    paddingVertical: 10,
  },
  resendRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 12,
  },
  resendText: { color: '#767676', fontSize: 14, flex: 1 },
  resendButton: { color: '#2379FA', fontSize: 14 },
  button: {
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
  },
  buttonText: { fontSize: 16, fontWeight: 'bold' },
});
