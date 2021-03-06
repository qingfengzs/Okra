/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.okra.example.game.command.impl;

import org.ogcs.app.Session;
import org.ogcs.okra.example.game.command.AbstractCommand;
import org.ogcs.okra.example.game.conf.ServerProperties;
import org.ogcs.okra.example.game.generated.Example;
import org.ogcs.okra.example.game.generated.Gpb;
import org.ogcs.okra.example.game.generated.Gpb.Request;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.server.DefaultRole;

/**
 * create role command
 */
public class GAME_CREATE extends AbstractCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        Example.MsgRegister msgRegister = Example.MsgRegister.parseFrom(request.getData());

        // TODO: Get role by account
        MemAccount memAccount = roleMapper.select(msgRegister.getAccount());
        if (memAccount != null) {
            // DefaultRole is exist. Error
            return;
        }
        memAccount = new MemAccount();
        memAccount.setAccount(msgRegister.getAccount());
//        memAccount.setName(msgRegister.getName());
        memAccount.setUid(ServerProperties.id());
//        memAccount.setPsw(msgRegister.getPsw());
        //
        roleMapper.insert(memAccount);

        // TODO: do some logic content
        DefaultRole player = new DefaultRole(session, memAccount);
        // session set player.
        // The player's function disconnect()  will be invoked, When the session is inactive.
        session.setConnector(player);

        session.writeAndFlush(Gpb.Response.newBuilder()
                .setId(request.getId())
                .setData(request.getData())
                .build());
    }
}
