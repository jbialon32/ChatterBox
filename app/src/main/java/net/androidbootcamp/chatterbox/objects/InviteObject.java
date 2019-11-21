package net.androidbootcamp.chatterbox.objects;

import net.androidbootcamp.chatterbox.inviteGen.InviteGenerator;

public class InviteObject {
    private int id;
    private String inviteCode;
    private boolean perm;

    public InviteObject(int chatID){

        id = chatID;
        perm = false;
        inviteCode = InviteGenerator.Generate();

    }

    public InviteObject(int chatID, boolean permanent){

        id = chatID;
        perm = permanent;
        inviteCode = InviteGenerator.Generate();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean isPerm() {
        return perm;
    }

    public void setPerm(boolean perm) {
        this.perm = perm;
    }
}
