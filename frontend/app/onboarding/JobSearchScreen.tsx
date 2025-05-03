import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TextInput,
  ScrollView,
  StyleSheet,
  TouchableOpacity,
  Image,
} from 'react-native';
import { useRouter } from 'expo-router';
import { searchJobs } from '@/api/Onboarding';

export default function JobSearchScreen() {
  const [keyword, setKeyword] = useState('');
  const [results, setResults] = useState<{ name: string }[]>([]);
  const router = useRouter();

  useEffect(() => {
    const delayDebounce = setTimeout(() => {
      if (keyword.trim()) {
        searchJobs(keyword).then(setResults);
      } else {
        setResults([]);
      }
    }, 300);
    return () => clearTimeout(delayDebounce);
  }, [keyword]);

  const highlightText = (text: string) => {
    const parts = text.split(new RegExp(`(${keyword})`, 'gi'));
    return parts.map((part, i) =>
      part.toLowerCase() === keyword.toLowerCase() ? (
        <Text key={i} style={styles.highlight}>
          {part}
        </Text>
      ) : (
        <Text key={i}>{part}</Text>
      )
    );
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => router.back()}>
          <Image
            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
            style={styles.icon}
          />
        </TouchableOpacity>
      </View>

      <View style={styles.progressBarBackground}>
        <View style={styles.progressBar} />
      </View>

      <Text style={styles.title}>원하는 직무를 검색해보세요!</Text>

      <View style={styles.inputBox}>
        <Image
          source={require('@/assets/images/input-field-icon.png')}
          style={styles.inputIcon}
        />
        <TextInput
          style={styles.input}
          placeholder="원하는 직무를 검색해보세요"
          value={keyword}
          onChangeText={setKeyword}
          placeholderTextColor="#999"
        />
      </View>

      <View>
        {results.map((job, index) => (
          <TouchableOpacity key={index} style={styles.resultBox}>
            {highlightText(job.name)}
          </TouchableOpacity>
        ))}
      </View>

      <TouchableOpacity style={styles.completeBtn}>
        <Text style={styles.completeText}>선택 완료</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#fff', padding: 20 },
  header: { paddingTop: 40, paddingBottom: 16 },
  icon: { width: 24, height: 24 },
  progressBarBackground: {
    height: 6,
    backgroundColor: '#F1F1F5',
    borderRadius: 100,
    marginBottom: 24,
  },
  progressBar: {
    width: 110,
    height: 6,
    backgroundColor: '#2379FA',
    borderRadius: 100,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111',
    marginBottom: 24,
  },
  inputBox: {
    flexDirection: 'row',
    borderWidth: 1,
    borderColor: '#E5E5EC',
    borderRadius: 12,
    paddingHorizontal: 16,
    paddingVertical: 10,
    alignItems: 'center',
    marginBottom: 20,
  },
  inputIcon: { width: 20, height: 20, marginRight: 8 },
  input: {
    flex: 1,
    fontSize: 16,
    color: '#111',
  },
  resultBox: {
    paddingVertical: 14,
    borderBottomWidth: 1,
    borderBottomColor: '#EEE',
  },
  highlight: {
    color: '#2379FA',
    fontWeight: 'bold',
  },
  completeBtn: {
    backgroundColor: '#F7F7FB',
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
    marginTop: 40,
    marginBottom: 60,
  },
  completeText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#999',
  },
});
