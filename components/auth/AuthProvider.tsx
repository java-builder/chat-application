'use client';

import { useEffect, useState } from 'react';
import { useUserStore } from '@/store/useUserStore';
import { authService } from '@/services/auth.service';
import { Box, CircularProgress } from '@mui/material';

interface AuthProviderProps {
  children: React.ReactNode;
}

export default function AuthProvider({ children }: AuthProviderProps) {
  const [isInitialized, setIsInitialized] = useState(false);
  const { setUser, clearUser } = useUserStore();

  useEffect(() => {
    const initAuth = async () => {
      const token = localStorage.getItem('accessToken');
      
      if (token) {
        try {
          const response = await authService.myInfo();
          if (response.data) {
            setUser(response.data);
          }
        } catch (error) {
          console.error('Failed to fetch user info:', error);
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          clearUser();
        }
      }
      setIsInitialized(true);
    };

    initAuth();
  }, [setUser, clearUser]);

  if (!isInitialized) {
    return (
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          minHeight: '100vh',
          backgroundColor: '#36393f',
        }}
      >
        <CircularProgress sx={{ color: '#5865f2' }} />
      </Box>
    );
  }

  return <>{children}</>;
}
