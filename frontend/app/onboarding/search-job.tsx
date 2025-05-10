import React, { useState, useEffect } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  FlatList,
  StyleSheet,
} from 'react-native';
import { useRouter } from 'expo-router';
import { searchJobs } from '@/api/Onboarding';

export default function OnboardingJobScreen() {
  const router = useRouter();
  const [keyword, setKeyword] = useState('');
  const [searchResults, setSearchResults] = useState<{ code: string; name: string }[]>([]);
  const [selectedJob, setSelectedJob] = useState<{ code: string; name: string } | null>(null);

  useEffect(() => {
    const delayDebounce = setTimeout(() => {
      if (keyword.trim()) {
        searchJobs(keyword.trim())
          .then(setSearchResults)
          .catch(() => setSearchResults([]));
      } else {
        setSearchResults([]);
      }
    }, 300);
    return () => clearTimeout(delayDebounce);
  }, [keyword]);

  const handleComplete = () => {
    if (!selectedJob) return;
    router.push({
      pathname: '/onboarding/skill',
      params: { jobName: selectedJob.name },
    });
  };

  const highlightKeyword = (text: string) => {
    const parts = text.split(new RegExp(`(${keyword})`, 'gi'));
    return (
      <Text style={styles.resultText}>
        {parts.map((part, index) =>
          part.toLowerCase() === keyword.toLowerCase() ? (
            <Text key={index} style={styles.highlight}>
              {part}
            </Text>
          ) : (
            <Text key={index}>{part}</Text>
          )
        )}
      </Text>
    );
  };
  

  const handleSelectJob = (job: { code: string; name: string }) => {
    if (selectedJob?.code === job.code) {
      setSelectedJob(null);
    } else {
      setSelectedJob(job);
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        <TouchableOpacity onPress={() => router.back()} style={styles.backButton}>
          <Image
            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
            style={styles.backIcon}
          />
        </TouchableOpacity>

        <View style={styles.progressBarBackground}>
          <View style={styles.progressBarFill} />
        </View>

        <Text style={styles.title}>원하는 직무를 검색해보세요!</Text>

        <View style={styles.searchBox}>
          <Image source={require('@/assets/images/search-icon.png')} style={styles.searchIcon} />
          <TextInput
            placeholder="원하는 직무를 검색해보세요"
            value={keyword}
            onChangeText={setKeyword}
            style={styles.input}
            placeholderTextColor="#999999"
          />
          {keyword.length > 0 && (
            <TouchableOpacity onPress={() => setKeyword('')}>
              <Image
                source={require('@/assets/images/right-icon-wrapper.png')}
                style={styles.clearIcon}
              />
            </TouchableOpacity>
          )}
        </View>

        {keyword.length > 0 && searchResults.length === 0 && (
          <View style={styles.emptyWrapper}>
            <Image
              source={require('@/assets/images/tossface-qurious-100.png')}
              style={styles.emptyImage}
            />
            <Text style={styles.emptyTitle}>검색 결과가 없습니다</Text>
            <Text style={styles.emptyDesc}>
              다른 키워드로 검색해 보세요!{"\n"}더 다양한 직업을 소개해드릴 수 있도록{"\n"}정보는 계속 업데이트 중이에요.
            </Text>
          </View>
        )}

        <FlatList
          data={searchResults}
          keyExtractor={item => item.code}
          renderItem={({ item }) => {
            const isSelected = selectedJob?.code === item.code;
            return (
              <TouchableOpacity
                style={[
                  styles.resultItem,
                  isSelected && styles.selectedItemGray, 
                ]}
                onPress={() => setSelectedJob(isSelected ? null : item)} 
              >
                <View style={styles.resultItemContent}>
                  {highlightKeyword(item.name)}
                  {isSelected && (
                    <Image
                      source={require('@/assets/images/checkbox.png')}
                      style={styles.checkIcon}
                    />
                  )}
                </View>
              </TouchableOpacity>
            );
          }}
          
          style={styles.resultList}
          contentContainerStyle={{ paddingHorizontal: 20 }}
          keyboardShouldPersistTaps="handled"
        />

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: selectedJob ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleComplete}
          disabled={!selectedJob}
        >
          <Text
            style={[
              styles.buttonText,
              { color: selectedJob ? '#FFFFFF' : '#999999' },
            ]}
          >
            선택 완료
          </Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  container: {
    flex: 1,
    paddingHorizontal: 20,
  },
  backButton: {
    marginTop: 20,
    marginBottom: 12,
    width: 28,
    height: 28,
  },
  backIcon: {
    width: 28,
    height: 28,
    resizeMode: 'contain',
  },
  progressBarBackground: {
    height: 6,
    backgroundColor: '#F1F1F5',
    borderRadius: 100,
    marginBottom: 32,
  },
  progressBarFill: {
    width: 111,
    height: 6,
    backgroundColor: '#2379FA',
    borderRadius: 100,
  },
  title: {
    fontSize: 20,
    fontWeight: '700',
    color: '#111111',
    marginBottom: 24,
  },
  searchBox: {
    flexDirection: 'row',
    alignItems: 'center',
    borderColor: '#E5E5EC',
    borderWidth: 1,
    borderRadius: 12,
    paddingHorizontal: 8,
    height: 48,
    marginBottom: 12,
  },
  searchIcon: {
    width: 50,
    height: 50,
    marginRight: 2,
    resizeMode: 'contain',
  },
  selectedItemGray: {
    backgroundColor: '#F1F1F5',
  },
  checkIcon: {
    width: 30,
    height: 30,
    resizeMode: 'contain',
  },
  clearIcon: {
    width: 40,
    height: 40,
    marginLeft: 3,
    resizeMode: 'contain',
  },
  input: {
    flex: 1,
    fontSize: 16,
    color: '#111111',
  },
  resultList: {
    marginTop: 8,
    marginBottom: 12,
  },
  resultItem: {
    paddingVertical: 12,
    paddingHorizontal: 16,
    borderRadius: 8,
    backgroundColor: '#FFFFFF',
    marginBottom: 8,
  },
  selectedItem: {
    backgroundColor: '#F1F1F5',
  },
  resultItemContent: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  resultText: {
    fontSize: 16,
    color: '#111',
  },
  highlight: {
    fontSize: 16,
    color: '#2379FA',
    fontWeight: '600',
  },
  checkMark: {
    color: '#2379FA',
    fontSize: 16,
    fontWeight: 'bold',
  },
  button: {
    marginTop: 'auto',
    marginBottom: 46,
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
  },
  buttonText: {
    fontSize: 16,
    fontWeight: '700',
  },
  emptyWrapper: {
    alignItems: 'center',
    marginTop: 60,
    marginBottom: 40,
  },
  emptyImage: {
    width: 80,
    height: 80,
    marginBottom: 20,
    resizeMode: 'contain',
  },
  emptyTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#111',
    marginBottom: 8,
  },
  emptyDesc: {
    fontSize: 14,
    color: '#767676',
    textAlign: 'center',
    lineHeight: 22,
  },
});
