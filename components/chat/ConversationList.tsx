'use client';

import { Box, List, ListItemButton, ListItemAvatar, Avatar, ListItemText, Typography, CircularProgress } from '@mui/material';
import { ConversationDetailResponse } from '@/types';
import { formatDistanceToNow } from 'date-fns';
import { vi } from 'date-fns/locale';

interface ConversationListProps {
  conversations: ConversationDetailResponse[];
  selectedId: string | null;
  onSelect: (id: string) => void;
  loading?: boolean;
}

export default function ConversationList({
  conversations,
  selectedId,
  onSelect,
  loading = false,
}: ConversationListProps) {
  const getConversationName = (conv: ConversationDetailResponse) => {
    if (conv.name) return conv.name;
    return conv.participantInfo?.map(p => p.username).join(', ') || 'Conversation';
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', p: 4 }}>
        <CircularProgress sx={{ color: '#5865f2' }} />
      </Box>
    );
  }

  return (
    <List sx={{ p: 0 }}>
      {conversations.map((conv) => (
        <ListItemButton
          key={conv.id}
          selected={selectedId === conv.id}
          onClick={() => onSelect(conv.id)}
          sx={{
            px: 2,
            py: 1,
            borderRadius: 1,
            mx: 1,
            mb: 0.5,
            '&.Mui-selected': {
              backgroundColor: '#404249',
              '&:hover': {
                backgroundColor: '#404249',
              },
            },
            '&:hover': {
              backgroundColor: '#35373c',
            },
          }}
        >
          <ListItemAvatar sx={{ minWidth: 48 }}>
            <Avatar
              src={conv.conversationAvatar || undefined}
              sx={{
                bgcolor: '#5865f2',
                width: 36,
                height: 36,
                fontSize: '0.9rem',
              }}
            >
              {getConversationName(conv)[0]?.toUpperCase()}
            </Avatar>
          </ListItemAvatar>
          <ListItemText
            primary={
              <Typography
                component="span"
                sx={{
                  color: '#fff',
                  fontWeight: 600,
                  fontSize: '0.875rem',
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                  whiteSpace: 'nowrap',
                  display: 'block',
                }}
              >
                {getConversationName(conv)}
              </Typography>
            }
            secondary={
              <Box component="span" sx={{ display: 'block' }}>
                <Typography
                  component="span"
                  sx={{
                    color: '#b9bbbe',
                    fontSize: '0.8125rem',
                    overflow: 'hidden',
                    textOverflow: 'ellipsis',
                    whiteSpace: 'nowrap',
                    display: 'block',
                  }}
                >
                  {conv.lastMessageContent || 'Chưa có tin nhắn'}
                </Typography>
                {conv.lastMessageTime && (
                  <Typography
                    component="span"
                    sx={{
                      color: '#72767d',
                      fontSize: '0.6875rem',
                      mt: 0.25,
                      display: 'block',
                    }}
                  >
                    {formatDistanceToNow(new Date(conv.lastMessageTime), {
                      addSuffix: true,
                      locale: vi,
                    })}
                  </Typography>
                )}
              </Box>
            }
          />
        </ListItemButton>
      ))}
    </List>
  );
}
