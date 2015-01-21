package com.gildedgames.util.group.common;

import com.gildedgames.util.group.common.player.GroupMember;


public enum GroupPermissions
{
	
	OPEN()
	{
		@Override
		public String getName()
		{
			return "group.open.name";
		}

		@Override
		public String getDescription()
		{
			return "group.open.description";
		}

		@Override
		public boolean canBeInvited(GroupMember member)
		{
			return true;
		}
		
		@Override
		public boolean canJoin(GroupMember member)
		{
			return true;
		}

		@Override
		public boolean isVisible()
		{
			return true;
		}
	},
	
	CLOSED()
	{
		@Override
		public String getName()
		{
			return "group.closed.name";
		}

		@Override
		public String getDescription()
		{
			return "group.closed.description";
		}

		@Override
		public boolean canBeInvited(GroupMember member)
		{
			return true;
		}
		
		@Override
		public boolean canJoin(GroupMember member)
		{
			return false;
		}

		@Override
		public boolean isVisible()
		{
			return true;
		}
	},
	
	PRIVATE()
	{
		@Override
		public String getName()
		{
			return "group.private.name";
		}

		@Override
		public String getDescription()
		{
			return "group.private.description";
		}

		@Override
		public boolean canBeInvited(GroupMember member)
		{
			return true;
		}
		
		@Override
		public boolean canJoin(GroupMember member)
		{
			return false;
		}

		@Override
		public boolean isVisible()
		{
			return false;
		}
	};

	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract boolean canBeInvited(GroupMember member);
	
	public abstract boolean canJoin(GroupMember member);
	
	public abstract boolean isVisible();
	
}
